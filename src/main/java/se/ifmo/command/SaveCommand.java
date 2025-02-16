package se.ifmo.command;

import se.ifmo.exception.IORuntimeException;
import se.ifmo.io.JsonWriter;
import se.ifmo.io.Writer;
import se.ifmo.io.impl.JsonWriterImpl;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.dump.DatabaseDump;
import se.ifmo.preset.DefaultConfiguration;
import se.ifmo.receiver.Receiver;
import java.io.File;

/**
 * Команда для сохранения данных в файл.
 * Она выполняет проверку пути и записи в файл, а затем сохраняет данные из базы данных в формате JSON.
 */
public class SaveCommand extends WithoutParametersCommand  {
    private String path;

    /**
     * Конструктор команды сохранения.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     * @param path Путь к файлу, в который будут сохранены данные.
     */
    public SaveCommand(Receiver receiver, Writer writer, String path) {
        super(receiver, CommandConfiguration.SAVE_NAME, CommandConfiguration.SAVE_DESCRIPTION, writer);
        this.path = path;
    }

    /**
     * Выполняет команду сохранения базы данных в указанный файл или в файл по умолчанию.
     * Проверяет правильность пути и доступность записи в файл.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        JsonWriter<DatabaseDump> jsonWriter = new JsonWriterImpl<>();
        try {
            if (isValidPath(path)) {
                jsonWriter.writeToJson(receiver.getDatabaseDump(), path);
                writer.println(String.format("успешно сохранено в %s", path));
            } else {
                writer.println(String.format("попытка работы с backup путём -> %s", DefaultConfiguration.DEFAULT_PATH));
                if (isValidDefaultPath(DefaultConfiguration.DEFAULT_PATH)) {
                    jsonWriter.writeToJson(receiver.getDatabaseDump(), DefaultConfiguration.DEFAULT_PATH);
                    writer.println("успешно сохранено");
                }
            }
        } catch (IORuntimeException e) {
            writer.println("не получилось сохранить");
        }
    }

    /**
     * Проверяет, является ли путь по умолчанию допустимым.
     * Путь проверяется на существование, доступность записи и на то, что это файл.
     *
     * @param path Путь к файлу по умолчанию.
     * @return true, если путь валидный, иначе false.
     */
    private boolean isValidDefaultPath(String path) {
        File pFile = new File(path);
        File pDir = new File(pFile.getParent().toString());
        if (!isValidDirectoryPath(pDir)) {
            return false;
        }
        if (pFile.isFile()) {
            if (!pFile.canWrite()) {
                writer.println("Ошибка доступа записи");
                return false;
            }
        }
        return true;
    }

    /**
     * Проверяет, является ли указанный путь валидным.
     * Путь проверяется на существование, доступность записи и на то, что это файл.
     *
     * @param path Путь к файлу.
     * @return true, если путь валидный, иначе false.
     */
    private boolean isValidPath(String path) {
        if (path == null) {
            return false;
        }
        File pFile = new File(path);
        if (pFile.getParentFile() == null) {
            return false;
        }
        File pDir = new File(pFile.getParent().toString());
        if (!isValidDirectoryPath(pDir)) {
            return false;
        }
        if (!pFile.isFile()) {
            writer.println(String.format("Это не файл -> %s", pFile));
            return false;
        }
        if (!pFile.canWrite()) {
            writer.println(String.format("Ошибка доступа записи в -> %s", path));
            return false;
        }
        return true;
    }

    /**
     * Проверяет, является ли указанный путь директорией с правами на запись.
     *
     * @param pDir Путь к директории.
     * @return true, если путь валидный, иначе false.
     */
    private boolean isValidDirectoryPath(File pDir) {
        if (!pDir.isDirectory()) {
            writer.println(String.format("Это не директория -> %s", pDir));
            return false;
        }
        if (!pDir.canWrite()) {
            writer.println(String.format("Ошибка доступа записи в -> %s", pDir));
            return false;
        }
        return true;
    }
}
