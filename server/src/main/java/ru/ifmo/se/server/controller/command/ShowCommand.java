package ru.ifmo.se.server.controller.command;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseListLabWorkDto;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.mapper.LabWorkMapper;
import ru.ifmo.se.server.service.LabWorkService;

import java.util.List;

/**
 * Класс ShowCommand предназначен для вывода всех элементов коллекции LabWork.
 * Наследуется от WithoutParametersCommand.
 */
@Slf4j
public class ShowCommand extends AbstractCommand {

    /**
     * Конструктор ShowCommand.
     *
     * @param labWorkService объект, управляющий коллекцией.
     */
    public ShowCommand(LabWorkService labWorkService) {
        super(labWorkService, CommandConfiguration.SHOW_NAME, CommandConfiguration.SHOW_DESCRIPTION, Condition.SECURE);
    }

    /**
     * Выполняет вывод всех элементов коллекции LabWork.
     * Если коллекция пуста, выводит соответствующее сообщение.
     */
    @Override
    public Response execute(Request request, User user) {
        List<LabWorkDto> list = labWorkService.getAll().stream().map(LabWorkMapper.INSTANCE::toDto).toList();
        return ResponseListLabWorkDto.builder()
                .answerType(AnswerType.SUCCESS)
                .labWorkList(list)
                .build();
    }
}
