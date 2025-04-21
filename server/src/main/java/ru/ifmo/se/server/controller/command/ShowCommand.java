package ru.ifmo.se.server.controller.command;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseListLabWorkDto;
import ru.ifmo.se.server.configuration.CommandConfiguration;
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
        super(labWorkService, CommandConfiguration.SHOW_NAME, CommandConfiguration.SHOW_DESCRIPTION);
    }

    /**
     * Выполняет вывод всех элементов коллекции LabWork.
     * Если коллекция пуста, выводит соответствующее сообщение.
     */
    @Override
    public Response execute(Request request) {
//        if (receiver.getSize() == 0) {
//            return Response.builder()
//                    .status(false)
//                    .message("Нет элементов в коллекции")
//                    .build();
//        }
        List<LabWorkDto> list = labWorkService.getAll().stream().map(LabWorkMapper.INSTANCE::toDto).toList();
        return ResponseListLabWorkDto.builder()
                .status(true)
                .labWorkList(list)
                .build();
    }
}
