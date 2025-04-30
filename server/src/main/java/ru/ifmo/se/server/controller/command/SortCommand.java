package ru.ifmo.se.server.controller.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

import java.util.Collections;
import java.util.List;

/**
 * Класс SortCommand предназначен для сортировки коллекции LabWork
 * в естественном порядке и вывода её элементов.
 * Наследуется от WithoutParametersCommand.
 */
public class SortCommand extends AbstractCommand {

    /**
     * Конструктор SortCommand.
     *
     * @param labWorkService объект, управляющий коллекцией.
     */
    public SortCommand(LabWorkService labWorkService) {
        super(labWorkService, CommandConfiguration.SORT_NAME, CommandConfiguration.SORT_DESCRIPTION, Condition.SECURE);
    }

    /**
     * Выполняет сортировку коллекции LabWork и выводит её элементы.
     * Если коллекция пуста, выводит соответствующее сообщение.
     */
    @Override
    public Response execute(Request request, User user) {
        if (labWorkService.getSize() == 0) {
            return Response.builder()
                    .answerType(AnswerType.ERROR)
                    .message("Нет элементов в коллекции -> нечего сортировать")
                    .build();
        }
        List<LabWork> list = labWorkService.getAll();
        Collections.sort(list);
        return Response.builder()
                .answerType(AnswerType.SUCCESS)
                .message("коллекция успешно отсортирована")
                .build();
    }
}
