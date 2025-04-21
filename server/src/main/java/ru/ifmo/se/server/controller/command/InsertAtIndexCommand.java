package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestIndex;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseLabWorkDto;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.mapper.LabWorkMapper;
import ru.ifmo.se.server.service.LabWorkService;

import java.time.LocalDate;

/**
 * Команда для вставки новой сущности в коллекцию по указанному индексу.
 * Если индекс больше, чем размер коллекции, выводится сообщение об ошибке.
 */
public class InsertAtIndexCommand extends AbstractCommand {

    /**
     * Конструктор команды вставки новой сущности по индексу.
     *
     * @param labWorkService Объект для взаимодействия с базой данных.
     */
    public InsertAtIndexCommand(LabWorkService labWorkService) {
        super(labWorkService, CommandConfiguration.INSERT_AT_INDEX_NAME,
                CommandConfiguration.INSERT_AT_INDEX_DESCRIPTION);
    }

    /**
     * Выполняет команду вставки новой сущности в коллекцию по указанному индексу.
     * Если индекс больше, чем размер коллекции, выводится сообщение об ошибке.
     * Вставленная сущность получает уникальный id и текущую дату создания.
     */
    @Override
    public Response execute(Request request) {
        if (request instanceof RequestIndex) {
            RequestIndex requestIndex = (RequestIndex) request;
            Long index = requestIndex.getIndex();
            if (labWorkService.getAll().size() < index) {
                return Response.builder()
                        .status(false)
                        .message("в коллекции меньше элементов чем передаваемый индекс")
                        .build();
            } else {
                LabWork labWork = LabWorkMapper.INSTANCE.toEntity(requestIndex.getLabWorkDto());
                labWork.setId(labWorkService.getMaxId() + 1);
                labWork.setCreationDate(LocalDate.now());
                labWorkService.getAll().add(Math.toIntExact(index), labWork);
                return ResponseLabWorkDto.builder()
                        .status(true)
                        .labWorkDto(LabWorkMapper.INSTANCE.toDto(labWork))
                        .build();
            }
        }
        return Response.builder().status(false).build();
    }
}
