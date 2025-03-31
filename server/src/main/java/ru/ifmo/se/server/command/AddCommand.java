package ru.ifmo.se.server.command;

import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestLabWork;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseLabWorkDto;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.creator.LabWorkFieldValidator;
import ru.ifmo.se.server.exception.LabWorkDtoException;
import ru.ifmo.se.server.mapper.LabWorkMapper;
import ru.ifmo.se.server.receiver.Receiver;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Создаёт новый объект `LabWork` и добавляет его в коллекцию.
 */
public class AddCommand extends AbstractCommand {

    /**
     * Конструктор команды добавления нового элемента в коллекцию.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     */
    public AddCommand(Receiver receiver) {
        super(receiver, CommandConfiguration.ADD_NAME, CommandConfiguration.ADD_DESCRIPTION);
    }

    @Override
    public Response execute(Request request) {
        RequestLabWork requestLabWork = (RequestLabWork) request;
        LabWorkDto labWorkDto = requestLabWork.getLabWorkDto();
        LabWorkFieldValidator validator = new LabWorkFieldValidator();
        try {
            validator.validateLabWorkDto(labWorkDto);
        } catch (LabWorkDtoException e) {
            return Response.builder().status(false).message(e.getMessage()).build();
        }
        LabWork labWork = LabWorkMapper.INSTANCE.toEntity(labWorkDto);
        labWork = receiver.add(labWork);
        LabWorkDto lastLabWorkDto = LabWorkMapper.INSTANCE.toDto(labWork);
        return ResponseLabWorkDto.builder()
                .status(true)
                .labWorkDto(lastLabWorkDto).build();
    }
}
