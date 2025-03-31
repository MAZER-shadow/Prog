package ru.ifmo.se.server.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.receiver.Receiver;

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
     * @param receiver объект, управляющий коллекцией.
     */
    public SortCommand(Receiver receiver) {
        super(receiver, CommandConfiguration.SORT_NAME, CommandConfiguration.SORT_DESCRIPTION);
    }

    /**
     * Выполняет сортировку коллекции LabWork и выводит её элементы.
     * Если коллекция пуста, выводит соответствующее сообщение.
     */
    @Override
    public Response execute(Request request) {
        if (receiver.getSize() == 0) {
            return Response.builder()
                    .status(false)
                    .message("Нет элементов в коллекции -> нечего сортировать")
                    .build();
        }
        List<LabWork> list = receiver.getAll();
        Collections.sort(list);
        return Response.builder()
                .status(true)
                .message("коллекция успешно отсортирована")
                .build();
    }
}
