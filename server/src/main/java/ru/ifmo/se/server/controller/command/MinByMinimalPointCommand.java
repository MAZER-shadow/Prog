package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseLabWorkDto;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.mapper.LabWorkMapper;
import ru.ifmo.se.server.service.LabWorkService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Команда для поиска сущности с минимальным значением минимальной точки.
 * Сортирует коллекцию по минимальной точке и выводит информацию о первой сущности.
 */
public class MinByMinimalPointCommand extends AbstractCommand {

    /**
     * Конструктор команды поиска сущности с минимальной минимальной точкой.
     *
     * @param labWorkService Объект для взаимодействия с базой данных.
     */
    public MinByMinimalPointCommand(LabWorkService labWorkService) {
        super(labWorkService, CommandConfiguration.MIN_BY_MINIMAL_POINT_NAME,
                CommandConfiguration.MIN_BY_MINIMAL_POINT_DESCRIPTION, Condition.SECURE);
    }

    /**
     * Выполняет команду поиска сущности с минимальной минимальной точкой.
     * Сортирует все элементы коллекции по минимальной точке и выводит информацию
     * о первой сущности в отсортированном списке.
     */
    @Override
    public Response execute(Request request, User user) {
        List<LabWork> list = new ArrayList<>(labWorkService.getAll());
        if (list.isEmpty()) {
            return Response.builder()
                    .status(false)
                    .message("Коллекция пуста")
                    .build();
        }
        list.sort(Comparator.comparingDouble(LabWork::getMinimalPoint));
        return ResponseLabWorkDto.builder()
                .status(true)
                .labWorkDto(LabWorkMapper.INSTANCE.toDto(list.get(0)))
                .build();
    }
}
