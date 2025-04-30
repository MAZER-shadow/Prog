package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

/**
 * Команда для очистки всей коллекции.
 * Удаляет все элементы из коллекции, делая её пустой.
 */
public class ClearCommand extends AbstractCommand {

    /**
     * Конструктор команды очистки коллекции.
     *
     * @param labWorkService Объект для взаимодействия с базой данных.
     */
    public ClearCommand(LabWorkService labWorkService) {
        super(labWorkService, CommandConfiguration.CLEAR_NAME, CommandConfiguration.CLEAR_DESCRIPTION, Condition.SECURE);
    }

    @Override
    public Response execute(Request request, User user) {
        labWorkService.clear(user);
        return Response.builder()
                .answerType(AnswerType.SUCCESS)
                .message("Коллекция очищена")
                .build();
    }
}
