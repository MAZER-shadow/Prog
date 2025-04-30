package ru.ifmo.se.client.command;

import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseMap;
import ru.ifmo.se.common.io.Writer;

/**
 * Команда для группировки элементов коллекции по минимальной оценке и подсчета их количества.
 * Выводит количество элементов для каждой уникальной минимальной точки.
 */
public class GroupCountingByMinimalPointCommand extends WithoutParametersCommand {

    /**
     * Конструктор команды группировки и подсчета элементов по минимальной оценке
     *
     * @param writer Писатель для вывода сообщений.
     */
    public GroupCountingByMinimalPointCommand(Writer writer) {
        super(CommandConfiguration.GROUP_COUNTING_BY_MINIMAL_POINT_NAME,
                CommandConfiguration.GROUP_COUNTING_BY_MINIMAL_POINT_DESCRIPTION, writer, true);
    }

    /**
     * Выполняет команду группировки элементов по минимальной оценке и выводит количество
     * элементов для каждой уникальной минимальной оценке.
     * Если коллекция пуста, выводится соответствующее сообщение.
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
        if (response instanceof ResponseMap) {
            ResponseMap responseMap = (ResponseMap) response;
            responseMap.getResponse().forEach((minimalPoint, count) ->
                    writer.println("minimalPoint: " + minimalPoint + " -> Количество: " + count)
            );
        }
    }
}
