package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Writer;

/**
 * Команда для удаления сущности по идентификатору.
 * Проверяет корректность идентификатора и удаляет сущность из коллекции.
 */
public class RemoveByIdCommand extends WithParametersCommand {

    /**
     * Конструктор команды удаления сущности по идентификатору.
     *
     * @param writer Писатель для вывода сообщений.
     */
    public RemoveByIdCommand(Writer writer) {
        super(CommandConfiguration.REMOVE_BY_ID_NAME, CommandConfiguration.REMOVE_BY_ID_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду удаления сущности по заданному идентификатору.
     * Проверяет, существует ли сущность с таким идентификатором, и если да, удаляет её.
     * Если идентификатор некорректен или сущность не найдена, выводится сообщение об ошибке.
     *
     * @param parameter Параметр команды — идентификатор сущности для удаления.
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
