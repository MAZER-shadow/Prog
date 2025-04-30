package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.client.util.LabWorkDtoPrinter;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseListLabWorkDto;
import ru.ifmo.se.common.io.Writer;

import java.util.List;

/**
 * Класс ShowCommand предназначен для вывода всех элементов коллекции LabWork.
 * Наследуется от WithoutParametersCommand.
 */
public class ShowCommand extends WithoutParametersCommand {
    private final LabWorkDtoPrinter printer = new LabWorkDtoPrinter();

    /**
     * Конструктор ShowCommand.
     *
     * @param writer объект для вывода информации пользователю.
     */
    public ShowCommand(Writer writer) {
        super(CommandConfiguration.SHOW_NAME, CommandConfiguration.SHOW_DESCRIPTION, writer, true);
    }

    /**
     * Выполняет вывод всех элементов коллекции LabWork.
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
        if (response instanceof ResponseListLabWorkDto) {
            ResponseListLabWorkDto responseListLabWorkDto = (ResponseListLabWorkDto) response;
            List<LabWorkDto> labWorkList = responseListLabWorkDto.getLabWorkList();
            printer.printLabWorks(labWorkList, writer);
        }
    }
}
