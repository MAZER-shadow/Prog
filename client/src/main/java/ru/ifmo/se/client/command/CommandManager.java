package ru.ifmo.se.client.command;

import ru.ifmo.se.client.service.NetworkService;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.exception.CommandNotFoundException;
import ru.ifmo.se.common.exception.IORuntimeException;
import ru.ifmo.se.common.io.Writer;
import ru.ifmo.se.common.util.AnswerType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Класс CommandManager управляет регистрацией и выполнением команд.
 * Он хранит команды в карте и выполняет их на основе введенных пользователем данных.
 */
public class CommandManager {
    /**
     * Разделитель, используемый для разделения команды и её аргументов.
     */
    private static final String SEPARATOR = " ";

    /**
     * Карта для хранения зарегистрированных команд.
     */
    private final Map<String, AbstractCommand> commandMap = new HashMap<>();

    private final Map<String, AbstractCommand> specialCommandMap = new HashMap<>();

    /**
     * Объект для вывода сообщений.
     */
    private final Writer writer;

    /**
     * Позиция имени команды во входной строке.
     */
    private static final int POSITION_COMMAND_NAME_IN_INPUT = 0;

    /**
     * Позиция аргумента команды во входной строке.
     */
    private static final int POSITION_ARGUMENT_OF_COMMAND_IN_INPUT = 1;

    /**
     * Максимальное количество слов в команде (имя команды и аргумент).
     */
    private static final int SUM_WORDS_OF_COMMAND_NAME_AND_ARGUMENTS = 2;

    /**
     * Аргумент команды по умолчанию.
     */
    private static final String DEFAULT_ARGUMENT_OF_COMMAND = "";

    private final NetworkService networkService;

    public static String token;
    /**
     * Конструктор класса CommandManager. Инициализирует объект с указанным Writer.
     *
     * @param writer Объект Writer для вывода сообщений.
     */
    public CommandManager(Writer writer, NetworkService networkService) {
        this.writer = writer;
        this.networkService = networkService;
    }

    /**
     * Регистрирует команду в карте команд.
     *
     * @param abstractCommandName Имя команды.
     * @param abstractCommand     Объект команды, который будет зарегистрирован.
     */
    public void register(String abstractCommandName, AbstractCommand abstractCommand, boolean flag) {
        if (flag) {
            commandMap.put(abstractCommandName, abstractCommand);
        } else {
            specialCommandMap.put(abstractCommandName, abstractCommand);
        }
    }

    /**
     * Выполняет команду на основе введенных данных.
     *
     * @param input Входная строка, содержащая имя команды и её аргументы.
     * @throws CommandNotFoundException Если команда не найдена.
     */
    public void execute(String input) {
        try {
            input = input.stripLeading();
            String[] parameters = input.split(SEPARATOR);
            AbstractCommand abstractCommand;
            String abstractCommandName;
            if (token != null) {
                abstractCommandName = parameters[POSITION_COMMAND_NAME_IN_INPUT];
                abstractCommand = commandMap.get(abstractCommandName);
            } else {
                abstractCommandName = parameters[POSITION_COMMAND_NAME_IN_INPUT];
                abstractCommand = specialCommandMap.get(abstractCommandName);
            }
            if (parameters.length > SUM_WORDS_OF_COMMAND_NAME_AND_ARGUMENTS) {
                throw new CommandNotFoundException("Слишком много параметров -_-");
            }
            if (Objects.isNull(abstractCommand)) {
                throw new CommandNotFoundException(String.format("Не найдено команды: %s", abstractCommandName));
            }
            if (parameters.length == SUM_WORDS_OF_COMMAND_NAME_AND_ARGUMENTS) {
                Request request = abstractCommand.makeRequest(parameters[POSITION_ARGUMENT_OF_COMMAND_IN_INPUT]);
                if (request == null) {
                    return;
                }
                request.setToken(token);
                Response response = networkService.send(request);
                if (response.getAnswerType() == AnswerType.SUCCESS) {
                    abstractCommand.handleResponse(response);
                } else if (response.getAnswerType() == AnswerType.ERROR) {
                    writer.println(response.getMessage());
                } else if (response.getAnswerType() == AnswerType.REAUTHORIZATION) {
                    writer.println(response.getMessage());
                    token = null;
                }
            } else {
                Request request = abstractCommand.makeRequest(DEFAULT_ARGUMENT_OF_COMMAND);
                if (request == null) {
                    return;
                }
                request.setToken(token);
                Response response = networkService.send(request);
                if (response.getAnswerType() == AnswerType.SUCCESS) {
                    abstractCommand.handleResponse(response);
                } else if (response.getAnswerType() == AnswerType.ERROR) {
                    writer.println(response.getMessage());
                } else if (response.getAnswerType() == AnswerType.REAUTHORIZATION) {
                    writer.println(response.getMessage());
                    writer.println("Пожалуйста авторизуйтесь ещё раз");
                    token = null;
                }
            }
        } catch (IORuntimeException | CommandNotFoundException e) {
            writer.println(e.getMessage());
        }
    }
}
