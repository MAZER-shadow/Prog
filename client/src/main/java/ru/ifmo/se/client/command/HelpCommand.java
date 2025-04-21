package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Writer;

/**
 * Команда для вывода справки по доступным командам.
 * Выводит список всех команд и их описания.
 */
public class HelpCommand extends WithoutParametersCommand  {

    /**
     * Конструктор команды справки.
     * Инициализирует список доступных команд и их описания.
     * @param writer Писатель для вывода сообщений.
     */
    public HelpCommand(Writer writer) {
        super(CommandConfiguration.HELP_NAME, CommandConfiguration.HELP_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду вывода справки по доступным командам.
     * Выводит список команд с их описаниями.
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
