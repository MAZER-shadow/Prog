package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Writer;


import java.util.Collections;
import java.util.List;

/**
 * Класс SortCommand предназначен для сортировки коллекции LabWork
 * в естественном порядке и вывода её элементов.
 * Наследуется от WithoutParametersCommand.
 */
public class SortCommand extends WithoutParametersCommand  {

    /**
     * Конструктор SortCommand.
     * @param writer объект для вывода информации пользователю.
     */
    public SortCommand(Writer writer) {
        super(CommandConfiguration.SORT_NAME, CommandConfiguration.SORT_DESCRIPTION, writer);
    }

    /**
     * Выполняет сортировку коллекции LabWork и выводит её элементы.
     * Если коллекция пуста, выводит соответствующее сообщение.
     *
     * @param parameter параметр команды (не используется).
     */
    @Override
    public Request makeRequest(String parameter) {
        verifyParameter(parameter);
        return Request.builder()
                .commandName(name)
                .build();
    }

    @Override
    public void handleResponse(Response response) {
        writer.println(response.getMessage());
    }
}
