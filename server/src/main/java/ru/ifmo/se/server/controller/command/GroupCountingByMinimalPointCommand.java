package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseMap;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Команда для группировки элементов коллекции по минимальной оценке и подсчета их количества.
 * Выводит количество элементов для каждой уникальной минимальной точки.
 */
public class GroupCountingByMinimalPointCommand extends AbstractCommand {

    /**
     * Конструктор команды группировки и подсчета элементов по минимальной оценке.
     *
     * @param labWorkService Объект для взаимодействия с базой данных.
     */
    public GroupCountingByMinimalPointCommand(LabWorkService labWorkService) {
        super(labWorkService, CommandConfiguration.GROUP_COUNTING_BY_MINIMAL_POINT_NAME,
                CommandConfiguration.GROUP_COUNTING_BY_MINIMAL_POINT_DESCRIPTION, Condition.SECURE);
    }

    /**
     * Выполняет команду группировки элементов по минимальной оценке и выводит количество
     * элементов для каждой уникальной минимальной оценке.
     * Если коллекция пуста, выводится соответствующее сообщение.
     */
    @Override
    public Response execute(Request request, User user) {
        if (labWorkService.getAll().isEmpty()) {
            return Response.builder()
                    .answerType(AnswerType.ERROR)
                    .message("В коллекции нет элементов")
                    .build();
        } else {
            Map<Double, Long> map = labWorkService.getAll().stream()
                    .collect(Collectors.groupingBy(
                            LabWork::getMinimalPoint,
                            Collectors.counting()
                    ));
            return ResponseMap.builder()
                    .answerType(AnswerType.SUCCESS)
                    .response(map)
                    .build();
        }
    }
}
