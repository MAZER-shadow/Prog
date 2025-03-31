package ru.ifmo.se.server.command;

import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.receiver.Receiver;

import java.util.Map;

/**
 * Команда для вывода справки по доступным командам.
 * Выводит список всех команд и их описания.
 */
public class HelpCommand extends AbstractCommand {
    private StringBuilder resultCommand;

    /**
     * Конструктор команды справки.
     * Инициализирует список доступных команд и их описания.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param descriptionMap Карта, содержащая описание каждой команды.
     */
    public HelpCommand(Receiver receiver, Map<String, String> descriptionMap) {
        super(receiver, CommandConfiguration.HELP_NAME, CommandConfiguration.HELP_DESCRIPTION);
        doResultCommand(descriptionMap);
    }

    /**
     * Выполняет команду вывода справки по доступным командам.
     * Выводит список команд с их описаниями.
     */
    @Override
    public Response execute(Request request) {
        return Response.builder()
                .status(true)
                .message(resultCommand.toString())
                .build();
    }

    /**
     * Формирует строку с описаниями всех команд.
     * Для каждой команды из переданной карты добавляется строка с названием команды и ее описанием.
     *
     * @param descriptionMap Карта с описаниями команд.
     */
    private void doResultCommand(Map<String, String> descriptionMap) {
        resultCommand = new StringBuilder();
        descriptionMap.put(name, description);
        for (Map.Entry<String, String> stringStringEntry : descriptionMap.entrySet()) {
            resultCommand.append(stringStringEntry.getKey()).append(": ")
                    .append(stringStringEntry.getValue()).append("\n");
        }
    }
}
