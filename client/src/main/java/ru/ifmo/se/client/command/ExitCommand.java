package ru.ifmo.se.client.command;


import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Writer;

/**
 * Команда для завершения работы программы.
 * При выполнении этой команды программа завершает свое выполнение.
 */
public class ExitCommand extends WithoutParametersCommand {

    /**
     * Конструктор команды завершения работы программы.
     *
     * @param writer Писатель для вывода сообщений.
     */
    public ExitCommand(Writer writer) {
        super(CommandConfiguration.EXIT_NAME, CommandConfiguration.EXIT_DESCRIPTION, writer, true);
    }

    /**
     * Выполняет команду завершения работы программы.
     * После выполнения команды программа завершает выполнение с кодом 0.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public Request makeRequest(String parameter) {
        verifyParameter(parameter);
        writer.println("Завершение программы");
        System.exit(0);
        return null;
    }

    @Override
    public void handleResponse(Response response) {

    }
}