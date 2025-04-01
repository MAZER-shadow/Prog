package ru.ifmo.se.server.command.special;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.io.Writer;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.DefaultConfiguration;
import ru.ifmo.se.server.database.dump.DatabaseDump;
import ru.ifmo.se.server.io.JsonWriter;
import ru.ifmo.se.server.io.impl.JsonWriterImpl;
import ru.ifmo.se.server.receiver.Receiver;
import ru.ifmo.se.server.util.PathValidator;

/**
 * Команда для сохранения данных в файл.
 * Она выполняет проверку пути и записи в файл, а затем сохраняет данные из базы данных в формате JSON.
 */
@Slf4j
public class SaveCommand extends AbstractSpecialCommand {
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
     */
    @Override
    public void execute(String parameter) {
        verifyParameter(parameter);
        JsonWriter<DatabaseDump> jsonWriter = new JsonWriterImpl<>();
        PathValidator validator = new PathValidator(writer);
        log.info("Попытка сохранения");
        if (validator.isValidPathToSave(path)) {
            jsonWriter.writeToJson(receiver.getDatabaseDump(), path);
            log.info(String.format("успешное сохранение в -> %s", path));
        } else {
            log.info(String.format("попытка сохранить в запасной путь -> %s", DefaultConfiguration.DEFAULT_PATH));
            if (validator.isValidDefaultPathToSave()) {
                jsonWriter.writeToJson(receiver.getDatabaseDump(), DefaultConfiguration.DEFAULT_PATH);
                log.info(String.format("успешное сохранение в -> %s", DefaultConfiguration.DEFAULT_PATH));
            }
        }
    }
}
