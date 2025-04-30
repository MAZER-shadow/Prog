package ru.ifmo.se.client.command;


import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Writer;

/**
 * Команда для очистки всей коллекции.
 * Удаляет все элементы из коллекции, делая её пустой.
 */
public class ClearCommand extends WithoutParametersCommand {

    /**
     * Конструктор команды очистки коллекции.
     *
     * @param writer Писатель для вывода сообщений.
     */
    public ClearCommand(Writer writer) {
        super(CommandConfiguration.CLEAR_NAME, CommandConfiguration.CLEAR_DESCRIPTION, writer, true);
    }

    /**
     * Выполняет команду очистки коллекции, удаляя все элементы.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public Request makeRequest(String parameter) {
        verifyParameter(parameter);
        return Request.builder()
                .commandName(name)
                .build();
    }

    @Override
    public void handleResponse(Response response) {
        writer.println(response.getMessage());
    }
}
