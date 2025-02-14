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
import java.time.format.DateTimeParseException;
import java.util.*;

import static se.ifmo.preset.Starter.NAME_PATH_VARIABLE;

public class DatabaseDumpLoader {
    private final static String DEFAULT_PATH = "src/main/resources/FileWithLabWork";
    private final static String NAME_OF_COLLECTION = "LabWork";
    private final static String VALID_ERROR = "ошибка валидации поля";
    private final static String MESSAGE_ABOUT_END_WORK = "Завершение программы";
    private String errorField;
    private final JsonReader<DatabaseDump> jsonReader;
    private final Writer writer;
    @Getter
    private String path;

    public DatabaseDumpLoader(Writer writer, String path) {
        this.path = path;
        jsonReader =  new JsonReaderImpl<>(DatabaseDump.class);
        this.writer = writer;
        //валидации записи
        //проверить путь, есть ли -> то сказать об этом выдать путь куда я сохранил.
        //нет прав на запись
        //надо ещё написать валидацию на наличие всех полей и чтобы не было не
        // нужных полей в файле json, как это сделать пока что хз
    }

    public DatabaseDump exportDatabaseDump() {
        getValidationPath();
        return loadDatabaseDump();
    }

    private DatabaseDump loadDatabaseDump() {
        try {
            if (Objects.equals(path, DEFAULT_PATH)) {
                return getDefaultDatabaseDump();
            }
            DatabaseDump databaseDump = jsonReader.readJson(path);
            if (databaseDump == null) {
                writer.println(String.format("%s %s", VALID_ERROR, "databaseMetaDataDump"));
                runExitProcess();
            }
            if (!isValidateDatabaseMetaData(databaseDump)) {
                writer.println(String.format("%s %s", VALID_ERROR, "databaseMetaData"));
                runExitProcess();
            } else if (!isValidateListOfLabWork(databaseDump)) {
                databaseDump.setListOfLabWork(new ArrayList<>());
                if (databaseDump.getDatabaseMetaData().getSize() != 0) {
                    writer.println(String.format("%s %s", VALID_ERROR, "size"));
                    runExitProcess();
                }
            }
            writer.println(String.format("Успешное считывание с %s", path));
            return databaseDump;
        } catch (DateTimeParseException | JsonSyntaxException e) {
            writer.println("Ошибка преобразования в коллекцию(неподходящий формат поля)");
            runExitProcess();
        }
        return null;
    }

    private boolean isValidateDatabaseMetaData(DatabaseDump databaseDump) {
        DatabaseMetaData databaseMetaData = databaseDump.getDatabaseMetaData();
        if (databaseMetaData == null) {
            writer.println(String.format("%s %s", VALID_ERROR, "databaseMetaData"));
            runExitProcess();
        } else if (databaseMetaData.getClazz() == null) {
            writer.println(String.format("%s %s", VALID_ERROR, "clazz"));
            runExitProcess();
        } else if (databaseMetaData.getLocalDateTime() == null) {
            writer.println(String.format("%s %s", VALID_ERROR, "localDateTime"));
            runExitProcess();
        } else if (!databaseMetaData.getClazz().equals(NAME_OF_COLLECTION)) {
            writer.println(String.format("%s %s", VALID_ERROR, "clazz"));
            runExitProcess();
        } else if (!new FieldValidator().isValidDate(String.valueOf(databaseMetaData.getLocalDateTime()))) {
            writer.println(String.format("%s %s", VALID_ERROR, "localDateTime"));
            runExitProcess();
        }
        return true;
    }

    private boolean isValidateListOfLabWork(DatabaseDump databaseDump) {
        List<LabWork> listOfLabWork = databaseDump.getListOfLabWork();
        if (listOfLabWork == null) {
            return false;
        }
        if (databaseDump.getDatabaseMetaData().getSize() != listOfLabWork.size()) {
            writer.println(String.format("%s %s", VALID_ERROR, "size"));
            runExitProcess();
        }
        Set<Long> setId = new HashSet<>();
        for (LabWork labWork : listOfLabWork) {
            if (!validateLabWork(labWork, setId)) {
                writer.println(String.format("%s: %s", VALID_ERROR, errorField));
                runExitProcess();
            }
        }
        return true;
    }

    private DatabaseDump getDefaultDatabaseDump() {
        DatabaseMetaData metaDataDefault = DatabaseMetaData
                .builder()
                .clazz(NAME_OF_COLLECTION)
                .localDateTime(LocalDate.now())
                .size(0)
                .build();
        List<LabWork> list = new ArrayList<>();
        return new DatabaseDump(metaDataDefault, list);
    }

    private void getValidationPath() {
        if (path != null) {
            if (path.startsWith("~")) {
                String homeDir = System.getProperty("user.home");
                path = path.replaceFirst("~", homeDir);
            }
            Path absolutePath = Paths.get(path).toAbsolutePath().normalize();
            path = absolutePath.toString();
        }
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
            runExitProcess();
        }
        try (FileReader reader = new FileReader(path)) {
            JsonParser.parseReader(reader);
        } catch (JsonSyntaxException e) {
            writer.println(String.format("Файл %s находится в неподходящем формате для чтения", path));
            runExitProcess();
        } catch (IOException e) {
            writer.println("ошибка ввода: \\/|--|\\/");
            runExitProcess();
        }
    }

    private boolean validateLabWork(LabWork labWork, Set<Long> setId) {
        FieldValidator validator = new FieldValidator();
        if (!validator.isValidName(labWork.getName())) {
            errorField = "name";
            return false;
        } else if (labWork.getCoordinates() == null) {
            errorField = "coordinates";
            return false;
        } else if (!validator.isValidCoordinateX(String.valueOf(labWork.getCoordinates().getX()))) {
            errorField = "coordinateX";
            return false;
        } else if (!validator.isValidCoordinateY(String.valueOf(labWork.getCoordinates().getY()))) {
            errorField = "coordinateY";
            return false;
        } else if (labWork.getCreationDate() == null) {
            errorField = "creationDate";
            return false;
        } else if (!validator.isValidDate(labWork.getCreationDate().toString())) {
            errorField = "creationDate";
            return false;
        } else if (!validator.isValidMinimalPoint(String.valueOf(labWork.getMinimalPoint()))) {
            errorField = "minimalPoint";
            return false;
        } else if (!validator.isValidMaximumPoint(String.valueOf(labWork.getMaximumPoint()))) {
            errorField = "maximumPoint";
            return false;
        } else if (!(labWork.getDifficulty() == null)) {
            if (!validator.isValidDifficulty(String.valueOf(labWork.getDifficulty()))) {
                errorField = "difficulty";
                return false;
            }
        }
        if (labWork.getAuthor() == null) {
            errorField = "author";
            return false;
        } else if (!validator.isValidName(labWork.getAuthor().getName())) {
            errorField = "the author's name";
            return false;
        } else if (!(labWork.getAuthor().getHeight() == null)) {
            if (!validator.isValidHeight(String.valueOf(labWork.getAuthor().getHeight()))) {
                errorField = "the author's height";
                return false;
            }
        }
        if (!validator.isValidPassportID(String.valueOf(labWork.getAuthor().getPassportID()))) {
            errorField = "the author's passportID";
            return false;
        } else if (!validator.isValidId(String.valueOf(labWork.getId()), setId)) {
            errorField = "id";
            return false;
        }
        return true;
    }

    private void runExitProcess() {
        writer.println(MESSAGE_ABOUT_END_WORK);
        System.exit(0);
    }
}
