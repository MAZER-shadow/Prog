package ru.ifmo.se.server.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.receiver.Receiver;

/**
 * Команда для удаления первого элемента из коллекции.
 * Если коллекция пуста, выводится сообщение об ошибке.
 */
public class RemoveFirstCommand extends AbstractCommand {

    /**
     * Конструктор команды удаления первого элемента.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     */
    public RemoveFirstCommand(Receiver receiver) {
        super(receiver, CommandConfiguration.REMOVE_FIRST_NAME, CommandConfiguration.REMOVE_FIRST_DESCRIPTION);
    }

    /**
     * Выполняет команду удаления первого элемента из коллекции.
     * Если коллекция пуста, выводится сообщение о том, что сущности отсутствуют.
     *
     */
    @Override
    public Response execute(Request request) {
        if (receiver.getAll().isEmpty()) {
            return Response.builder()
                    .status(false)
                    .message("В коллекции нет сущностей")
                    .build();
        } else {
            long id = receiver.getAll().get(0).getId();
            receiver.removeById(id);
            return Response.builder()
                    .status(true)
                    .message("Успешное удаление")
                    .build();
        }
    }
}
