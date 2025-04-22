package ru.ifmo.se.server.controller.command;

import lombok.SneakyThrows;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

import java.util.List;
import java.util.Map;

/**
 * Команда для вывода справки по доступным командам.
 * Выводит список всех команд и их описания.
 */
public class HelpCommand extends AbstractCommand {
    private StringBuilder resultSecureCommand;
    private StringBuilder resultInsecureCommand;

    /**
     * Конструктор команды справки.
     * Инициализирует список доступных команд и их описания.
     *
     * @param labWorkService Объект для взаимодействия с базой данных.
     */
    public HelpCommand(LabWorkService labWorkService, List<AbstractCommand> listCommand) {
        super(labWorkService, CommandConfiguration.HELP_NAME, CommandConfiguration.HELP_DESCRIPTION, Condition.INSECURE);
        doResultCommand(listCommand);
    }

    /**
     * Выполняет команду вывода справки по доступным командам.
     * Выводит список команд с их описаниями.
     */
    @SneakyThrows
    @Override
    public Response execute(Request request, User user) {
        if (user == null) {
            return Response.builder()
                    .status(true)
                    .message(resultInsecureCommand.toString())
                    .build();
        } else {
            return Response.builder()
                    .status(true)
                    .message(resultSecureCommand.toString())
                    .build();
        }
    }

    /**
     * Формирует строку с описаниями всех команд.
     * Для каждой команды из переданной карты добавляется строка с названием команды и ее описанием.
     *
     */
    private void doResultCommand(List<AbstractCommand> listCommand) {
        resultInsecureCommand = new StringBuilder();
        resultSecureCommand = new StringBuilder();
        for (AbstractCommand command : listCommand) {
            if (command.getCondition() == Condition.INSECURE) {
                resultInsecureCommand.append(command.name).append(": ").append(command.description).append("\n");
            }
            else {
                resultSecureCommand.append(command.name).append(": ").append(command.description).append("\n");
            }
        }
    }
}
