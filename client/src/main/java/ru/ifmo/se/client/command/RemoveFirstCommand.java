package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Writer;

/**
 * Команда для удаления первого элемента из коллекции.
 * Если коллекция пуста, выводится сообщение об ошибке.
 */
public class RemoveFirstCommand extends WithoutParametersCommand {

    /**
     * Конструктор команды удаления первого элемента.
     *
     * @param writer Писатель для вывода сообщений.
     */
    public RemoveFirstCommand(Writer writer) {
        super(CommandConfiguration.REMOVE_FIRST_NAME, CommandConfiguration.REMOVE_FIRST_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду удаления первого элемента из коллекции.
     * Если коллекция пуста, выводится сообщение о том, что сущности отсутствуют.
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
