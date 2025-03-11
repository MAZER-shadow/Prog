package se.ifmo.command;

import se.ifmo.io.JsonWriter;
import se.ifmo.io.Writer;
import se.ifmo.io.impl.JsonWriterImpl;
import se.ifmo.configuration.CommandConfiguration;
import se.ifmo.database.dump.DatabaseDump;
import se.ifmo.configuration.DefaultConfiguration;
import se.ifmo.util.PathValidator;
import se.ifmo.receiver.Receiver;

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
        PathValidator validator = new PathValidator(writer);
        if (validator.isValidPathToSave(path)) {
            jsonWriter.writeToJson(receiver.getDatabaseDump(), path);
            writer.println(String.format("успешно сохранено в %s", path));
        } else {
            writer.println(String.format("попытка сохранить в запасной путь -> %s", DefaultConfiguration.DEFAULT_PATH));
            if (validator.isValidDefaultPathToSave()) {
                jsonWriter.writeToJson(receiver.getDatabaseDump(), DefaultConfiguration.DEFAULT_PATH);
                writer.println("успешно сохранено");
            }
        }
    }
}
