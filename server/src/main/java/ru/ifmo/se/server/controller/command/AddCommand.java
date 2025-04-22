package ru.ifmo.se.server.controller.command;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestLabWork;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseLabWorkDto;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.exception.LabWorkDtoException;
import ru.ifmo.se.server.mapper.LabWorkMapper;
import ru.ifmo.se.server.service.LabWorkService;
import ru.ifmo.se.server.service.validator.LabWorkFieldValidator;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Создаёт новый объект `LabWork` и добавляет его в коллекцию.
 */
@Slf4j
public class AddCommand extends AbstractCommand {

    /**
     * Конструктор команды добавления нового элемента в коллекцию.
     *
     * @param labWorkService Объект для взаимодействия с базой данных.
     */
    public AddCommand(LabWorkService labWorkService) {
        super(labWorkService, CommandConfiguration.ADD_NAME, CommandConfiguration.ADD_DESCRIPTION, Condition.SECURE);
    }

    @Override
    public Response execute(Request request, User user) {
        if (request instanceof RequestLabWork) {
            RequestLabWork requestLabWork = (RequestLabWork) request;
            LabWorkDto labWorkDto = requestLabWork.getLabWorkDto();
            LabWorkFieldValidator validator = new LabWorkFieldValidator();
            try {
                validator.validateLabWorkDto(labWorkDto);
            } catch (LabWorkDtoException e) {
                return Response.builder().status(false).message(e.getMessage()).build();
            }
            LabWork labWork = LabWorkMapper.INSTANCE.toEntity(labWorkDto);
            labWork.setUser(user);
            labWork = labWorkService.add(labWork);
            log.info(labWork.aboutLabWork());
            LabWorkDto lastLabWorkDto = LabWorkMapper.INSTANCE.toDto(labWork);
            return ResponseLabWorkDto.builder()
                    .status(true)
                    .labWorkDto(lastLabWorkDto).build();
        }
        return Response.builder().status(false).build();
    }
}
