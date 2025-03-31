package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
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
public class ShowCommand extends WithoutParametersCommand  {

    /**
     * Конструктор ShowCommand.
     * @param writer объект для вывода информации пользователю.
     */
    public ShowCommand(Writer writer) {
        super(CommandConfiguration.SHOW_NAME, CommandConfiguration.SHOW_DESCRIPTION, writer);
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
        ResponseListLabWorkDto responseListLabWorkDto = (ResponseListLabWorkDto) response;
        List<LabWorkDto> labWorkList = responseListLabWorkDto.getLabWorkList();
        for (LabWorkDto labWorkDto : labWorkList) {
            writer.println(labWorkDto.aboutLabWork());
        }
    }
}
