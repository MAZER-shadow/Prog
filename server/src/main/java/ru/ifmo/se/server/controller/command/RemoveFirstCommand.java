package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.User;
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
        super(labWorkService, CommandConfiguration.REMOVE_FIRST_NAME, CommandConfiguration.REMOVE_FIRST_DESCRIPTION, Condition.SECURE);
    }

    /**
     * Выполняет команду удаления первого элемента из коллекции.
     * Если коллекция пуста, выводится сообщение о том, что сущности отсутствуют.
     */
    @Override
    public Response execute(Request request, User user) {
        if (labWorkService.getAll().isEmpty()) {
            return Response.builder()
                    .answerType(AnswerType.ERROR)
                    .message("В коллекции нет сущностей")
                    .build();
        } else {
            long id = labWorkService.getAll().get(0).getId();
            labWorkService.removeById(id, user);
            return Response.builder()
                    .answerType(AnswerType.SUCCESS)
                    .message("Успешное удаление")
                    .build();
        }
    }
}
