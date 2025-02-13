package se.ifmo.preset;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import se.ifmo.create.FieldValidator;
import se.ifmo.data.DatabaseMetaData;
import se.ifmo.entity.LabWork;
import se.ifmo.io.JsonReader;
import se.ifmo.io.Writer;
import se.ifmo.io.impl.JsonReaderImpl;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirstDatabaseDumpGetter {
    private final static String NAME_PATH_VARIABLE = "PATH_FILE";
    private final static String DEFAULT_PATH = "src/main/resources/FileWithLabWork";
    private final static String NAME_OF_COLLECTION = "LabWork";
    private final static String VALID_ERROR = "ошибка валидации поля";
    private final static String DATE_PATTERN = "yyyy-MM-dd";
    private final JsonReader<DatabaseDump> jsonReader;
    private final Writer writer;
    @Getter
    private String path;

    public FirstDatabaseDumpGetter(Writer writer) {
        path = System.getenv(NAME_PATH_VARIABLE);
        jsonReader =  new JsonReaderImpl<>(DatabaseDump.class);
        this.writer = writer;
        setNormalPath();
        setValidatePath();
        //валидации чтения
        //задана ли переменная PATH_FILE -> сказать об этом (сделано)
        // и дать путь по которому будет сохранение (сделано)
        //есть ли файл по пути этой переменной -> сказать об этом(сделано)
        // и дать путь по которому будет сохранение(сделано)
        //можем ли прочитать(права) если нет сказать об этом(сделано) и выйти(сделано)
        //читаемость json -> то говорю(сделано) и выхожу(сделано)
        //валидация полей !всех!
        //починить сериализацию даты(LocalDate) (сделано)
        //валидации записи
        //проверить путь, есть ли -> то сказать об этом выдать путь куда я сохранил.
        //нет прав на запись
        //надо ещё написать валидацию на наличие всех полей и чтобы не было не
        // нужных полей в файле json, как это сделать пока что хз
    }

    public DatabaseDump getDatabaseDump() {
        try {
            if (Objects.equals(path, DEFAULT_PATH)) {
                return getDefaultDatabaseDump();
            }
            DatabaseDump databaseDump = jsonReader.readJson(path);
            if (databaseDump.getListOfLabWork() == null) {
                databaseDump.setListOfLabWork(new ArrayList<>());
                writer.println("Успешное считывание!");
                return databaseDump;
            }
            List<Long> listId = new ArrayList<>();
            for (LabWork labWork : databaseDump.getListOfLabWork()) {
                if (listId.contains(labWork.getId())) {
                    writer.println(VALID_ERROR);
                    System.exit(0);
                } else if (labWork.getId() <= 0) {
                    writer.println(VALID_ERROR);
                    System.exit(0);
                } else if (!validateLabWork(labWork)) {
                    writer.println(VALID_ERROR);
                    System.exit(0);
                }
            }
            writer.println("Успешное считывание!");
            return databaseDump;
        } catch (DateTimeParseException e) {
            writer.println(VALID_ERROR);
            System.exit(0);
        }
        return null;
    }

    private DatabaseDump getDefaultDatabaseDump() {
        DatabaseMetaData metaDataDefault = DatabaseMetaData
                .builder()
                .clazz(NAME_OF_COLLECTION)
                .localDateTime(String.valueOf(LocalDate.now()))
                .size(0)
                .build();
        List<LabWork> list = new ArrayList<>();
        return new DatabaseDump(metaDataDefault, list);
    }

    private void setNormalPath() {
        if (path != null) {
            if (path.startsWith("~")) {
                String homeDir = System.getProperty("user.home");
                path = path.replaceFirst("~", homeDir);
            }
            Path absolutePath = Paths.get(path).toAbsolutePath().normalize();
            path = absolutePath.toString();
        }
    }

    private void setValidatePath() {
        if (path == null || !Files.isRegularFile(Paths.get(path))) {
            Path resolvedPath = Paths.get(DEFAULT_PATH).toAbsolutePath();
            if (path != null) {
                writer.println(String.format("Путь %s не является файлом, новый путь записи: %s", path, resolvedPath));
                path = DEFAULT_PATH;
                return;
            }
            writer.println(String.format("Переменная окружения %s не задана, " +
                    "новый путь записи: %s", NAME_PATH_VARIABLE, resolvedPath));
            path = DEFAULT_PATH;
            return;
        }
        if (!Files.isReadable(Paths.get(path))) {
            writer.println(String.format("файл %s недоступен для чтения", path));
            writer.println("Завершение программы");
            System.exit(0);
        }
        try (FileReader reader = new FileReader(path)) {
            JsonParser.parseReader(reader);
        } catch (JsonSyntaxException e) {
            Path resolvedPath = Paths.get(DEFAULT_PATH).toAbsolutePath();
            writer.println(String.format("Файл %s находится в неподходящем формате для чтения", path, resolvedPath));
            writer.println("Завершение программы");
            System.exit(0);
        } catch (IOException e) {
            System.exit(0);
        }
    }

    private boolean validateLabWork(LabWork labWork) {
        FieldValidator validator = new FieldValidator();
        if (!validator.isValidName(labWork.getName())) {
            return false;
        } else if (!validator.isValidCoordinateX(String.valueOf(labWork.getCoordinates().getX()))) {
            return false;
        } else if (!validator.isValidCoordinateY(String.valueOf(labWork.getCoordinates().getY()))) {
            return false;
        } else if (!isValidFormatDate(labWork.getCreationDate().toString())) {
            return false;
        } else if (!validator.isValidMinimalPoint(String.valueOf(labWork.getMinimalPoint()))) {
            return false;
        } else if (!validator.isValidMaximumPoint(String.valueOf(labWork.getMaximumPoint()))) {
            return false;
        } else if (!validator.isValidDifficulty(String.valueOf(labWork.getDifficulty()))) {
            return false;
        } else if (!validator.isValidName(labWork.getAuthor().getName())) {
            return false;
        } else if (!validator.isValidHeight(String.valueOf(labWork.getAuthor().getHeight()))) {
            return false;
        } else return validator.isValidPassportID(String.valueOf(labWork.getAuthor().getPassportID()));
    }

    private boolean isValidFormatDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}