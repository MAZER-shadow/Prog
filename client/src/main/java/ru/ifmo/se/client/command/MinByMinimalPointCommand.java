package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseLabWorkDto;
import ru.ifmo.se.common.io.Writer;

/**
 * Команда для поиска сущности с минимальным значением минимальной точки.
 * Сортирует коллекцию по минимальной точке и выводит информацию о первой сущности.
 */
public class MinByMinimalPointCommand extends WithoutParametersCommand {

    /**
     * Конструктор команды поиска сущности с минимальной минимальной точкой.
     *
     * @param writer Писатель для вывода сообщений.
     */
    public MinByMinimalPointCommand(Writer writer) {
        super(CommandConfiguration.MIN_BY_MINIMAL_POINT_NAME,
                CommandConfiguration.MIN_BY_MINIMAL_POINT_DESCRIPTION, writer, true);
    }

    /**
     * Выполняет команду поиска сущности с минимальной минимальной точкой.
     * Сортирует все элементы коллекции по минимальной точке и выводит информацию
     * о первой сущности в отсортированном списке.
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
        if (response instanceof ResponseLabWorkDto) {
            writer.println(((ResponseLabWorkDto) response).getLabWorkDto().aboutLabWork());
        }
    }
}
