package ru.ifmo.se.server.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.receiver.Receiver;

/**
 * Команда для очистки всей коллекции.
 * Удаляет все элементы из коллекции, делая её пустой.
 */
public class ClearCommand extends AbstractCommand {

    /**
     * Конструктор команды очистки коллекции.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     */
    public ClearCommand(Receiver receiver) {
        super(receiver, CommandConfiguration.CLEAR_NAME, CommandConfiguration.CLEAR_DESCRIPTION);
    }

    @Override
    public Response execute(Request request) {
        receiver.clear();
        return Response.builder()
                .status(true)
                .message("Коллекция очищена")
                .build();
    }
}
