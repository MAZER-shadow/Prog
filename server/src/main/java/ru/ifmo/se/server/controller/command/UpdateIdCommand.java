package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestIndex;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.exception.EntityNotFoundException;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.mapper.LabWorkMapper;
import ru.ifmo.se.server.service.LabWorkService;

import java.time.LocalDate;

/**
 * Класс UpdateIdCommand предназначен для обновления элемента коллекции по заданному ID.
 * Наследуется от WithParametersCommand.
 */
public class UpdateIdCommand extends AbstractCommand {

    /**
     * Конструктор UpdateIdCommand.
     *
     * @param labWorkService объект, управляющий коллекцией.
     */
    public UpdateIdCommand(LabWorkService labWorkService) {
        super(labWorkService, CommandConfiguration.UPDATE_ID_NAME, CommandConfiguration.UPDATE_ID_DESCRIPTION, Condition.SECURE);
    }

    /**
     * Выполняет обновление элемента коллекции по заданному ID.
     * Если ID не найден, выводит сообщение об ошибке.
     */
    @Override
    public Response execute(Request request, User user) {
        if (request instanceof RequestIndex) {
            try {
                RequestIndex requestIndex = (RequestIndex) request;
                Long id = requestIndex.getIndex();
                if (!labWorkService.existById(id)) {
                    return Response.builder()
                            .answerType(AnswerType.ERROR)
                            .message("в коллекции меньше элементов чем передаваемый индекс")
                            .build();
                } else {
                    LabWork labWork = LabWorkMapper.INSTANCE.toEntity(requestIndex.getLabWorkDto());
                    labWork.setId(id);
                    labWork.setCreationDate(LocalDate.now());
                    labWorkService.updateById(id, labWork, user);
                    return Response.builder()
                            .answerType(AnswerType.SUCCESS)
                            .message("успешное обновление сущности")
                            .build();
                }
            } catch (NumberFormatException e) {
                return Response.builder()
                        .answerType(AnswerType.ERROR)
                        .message("Формат Id не целое число!")
                        .build();
            } catch (EntityNotFoundException e) {
                return Response.builder()
                        .answerType(AnswerType.ERROR)
                        .message(e.getMessage())
                        .build();
            }
        }
        return Response.builder().answerType(AnswerType.ERROR).build();
    }
}
