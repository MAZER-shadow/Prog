package se.ifmo.database.dump;

import com.google.gson.JsonSyntaxException;
import se.ifmo.database.data.DatabaseMetaData;
import se.ifmo.entity.LabWork;
import se.ifmo.exception.DumpDataBaseValidationException;
import se.ifmo.exception.FileReadException;
import se.ifmo.io.JsonReader;
import se.ifmo.io.impl.JsonReaderImpl;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для загрузки дампа базы данных из JSON-файла.
 */
public class DatabaseDumpLoader {
    /**
     * Тип коллекции, с которой работает загрузчик.
     */
    private static final String TYPE_OF_COLLECTION = "LabWork";

    /**
     * Имя переменной окружения, содержащей путь к файлу.
     */
    public final static String NAME_PATH_VARIABLE = "PATH_FILE";

    /**
     * Объект JsonReader для чтения дампа базы данных из JSON-файла.
     */
    private final JsonReader<DatabaseDump> jsonReader;

    /**
     * Конструктор класса DatabaseDumpLoader.
     * Инициализирует JsonReader для чтения объектов DatabaseDump.
     */
    public DatabaseDumpLoader() {
        jsonReader = new JsonReaderImpl<>(DatabaseDump.class);
    }

    /**
     * Загружает дамп базы данных из JSON-файла по указанному пути.
     *
     * @param incomingPath Путь к JSON-файлу.
     * @return Загруженный DatabaseDump.
     * @throws DumpDataBaseValidationException Если возникает ошибка при преобразовании JSON в коллекцию.
     */
    public DatabaseDump loadDatabaseDump(String incomingPath) {
        try {
            isValidFile(incomingPath);
            DatabaseDump databaseDump = jsonReader.readJson(incomingPath);
            return databaseDump;
        } catch (DateTimeParseException | JsonSyntaxException e) {
            throw new DumpDataBaseValidationException("Ошибка преобразования в коллекцию(неподходящий формат поля)");
        }
    }

    /**
     * Создает и возвращает DatabaseDump с метаданными по умолчанию и пустым списком LabWork.
     *
     * @return DatabaseDump с метаданными по умолчанию.
     */
    public DatabaseDump getDefaultDatabaseDump() {
        DatabaseMetaData metaDataDefault = DatabaseMetaData
                .builder()
                .clazz(TYPE_OF_COLLECTION)
                .localDateTime(LocalDate.now())
                .size(0)
                .build();
        List<LabWork> list = new ArrayList<>();
        return new DatabaseDump(metaDataDefault, list);
    }

    /**
     * Проверяет, является ли указанный путь к файлу допустимым.
     *
     * @param incomingPath Путь к файлу.
     * @throws FileReadException Если путь не задан, не является файлом или файл недоступен для чтения.
     */
    private void isValidFile(String incomingPath) {
        if (incomingPath == null) {
            throw new FileReadException(String.format("Переменная окружения %s не задана", NAME_PATH_VARIABLE));
        }
        if (!Files.isRegularFile(Paths.get(incomingPath))) {
            throw new FileReadException(String.format("Путь %s не является файлом или нет дост" +
                    "упа для чтения директории, в которой он находится", incomingPath));
        }
        if (!Files.isReadable(Paths.get(incomingPath))) {
            throw new FileReadException(String.format("файл %s недоступен для чтения", incomingPath));
        }
    }
}
