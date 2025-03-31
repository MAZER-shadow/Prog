package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Writer;


/**
 * Команда для вывода информации о метаданных базы данных.
 * Выводит описание метаданных базы данных, включая ее основные характеристики.
 */
public class InfoCommand extends WithoutParametersCommand  {

    /**
     * Конструктор команды вывода информации о базе данных.
     * @param writer Писатель для вывода сообщений.
     */
    public InfoCommand(Writer writer) {
        super(CommandConfiguration.INFO_NAME, CommandConfiguration.INFO_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду вывода информации о метаданных базы данных.
     * Выводит описание базы данных, предоставляемое объектом метаданных.
     *
     * @param parameter Параметр команды (не используется в данной команде).
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
