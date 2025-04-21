package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.service.LabWorkService;

/**
 * Команда для удаления первого элемента из коллекции.
 * Если коллекция пуста, выводится сообщение об ошибке.
 */
public class RemoveFirstCommand extends AbstractCommand {

    /**
     * Конструктор команды удаления первого элемента.
     *
     * @param labWorkService Объект для взаимодействия с базой данных.
     */
    public RemoveFirstCommand(LabWorkService labWorkService) {
        super(labWorkService, CommandConfiguration.REMOVE_FIRST_NAME, CommandConfiguration.REMOVE_FIRST_DESCRIPTION);
    }

    /**
     * Выполняет команду удаления первого элемента из коллекции.
     * Если коллекция пуста, выводится сообщение о том, что сущности отсутствуют.
     *
     */
    @Override
    public Response execute(Request request) {
        if (labWorkService.getAll().isEmpty()) {
            return Response.builder()
                    .status(false)
                    .message("В коллекции нет сущностей")
                    .build();
        } else {
            long id = labWorkService.getAll().get(0).getId();
            labWorkService.removeById(id);
            return Response.builder()
                    .status(true)
                    .message("Успешное удаление")
                    .build();
        }
    }
}
