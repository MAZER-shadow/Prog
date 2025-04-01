package ru.ifmo.se.server.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseMap;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.receiver.Receiver;

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
     * @param receiver Объект для взаимодействия с базой данных.
     */
    public GroupCountingByMinimalPointCommand(Receiver receiver) {
        super(receiver, CommandConfiguration.GROUP_COUNTING_BY_MINIMAL_POINT_NAME,
                CommandConfiguration.GROUP_COUNTING_BY_MINIMAL_POINT_DESCRIPTION);
    }

    /**
     * Выполняет команду группировки элементов по минимальной оценке и выводит количество
     * элементов для каждой уникальной минимальной оценке.
     * Если коллекция пуста, выводится соответствующее сообщение.
     */
    @Override
    public Response execute(Request request) {
        if (receiver.getAll().isEmpty()) {
            return Response.builder()
                    .status(false)
                    .message("В коллекции нет элементов")
                    .build();
        } else {
            Map<Double, Long> map = receiver.getAll().stream()
                    .collect(Collectors.groupingBy(
                            LabWork::getMinimalPoint,
                            Collectors.counting()
                    ));
            return ResponseMap.builder()
                    .status(true)
                    .response(map)
                    .build();
        }
    }
}
