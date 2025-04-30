package ru.ifmo.se.server.controller;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.exception.CommandNotFoundException;
import ru.ifmo.se.common.io.Writer;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.controller.command.AbstractCommand;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.exception.TokenTimeRuntimeException;
import ru.ifmo.se.server.service.AuthService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Класс CommandManager управляет регистрацией и выполнением команд.
 * Он хранит команды в карте и выполняет их на основе введенных пользователем данных.
 */
@Slf4j
public class CommandManager {
    /**
     * Разделитель, используемый для разделения команды и её аргументов.
     */
    private static final String SEPARATOR = " ";

    /**
     * Карта для хранения зарегистрированных команд.
     */
    private final Map<String, AbstractCommand> commandMap = new HashMap<>();

    /**
     * Объект для вывода сообщений.
     */
    private Writer writer;

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

    private final AuthService authService;
    /**
     * Конструктор класса CommandManager. Инициализирует объект с указанным Writer.
     *
     * @param writer Объект Writer для вывода сообщений.
     */
    public CommandManager(Writer writer, AuthService authService) {
        this.writer = writer;
        this.authService = authService;
    }

    /**
     * Регистрирует команду в карте команд.
     *
     * @param abstractCommandName Имя команды.
     * @param abstractCommand     Объект команды, который будет зарегистрирован.
     */
    public void register(String abstractCommandName, AbstractCommand abstractCommand) {
        commandMap.put(abstractCommandName, abstractCommand);
    }

    /**
     * Выполняет команду на основе введенных данных.
     *
     * @throws CommandNotFoundException Если команда не найдена.
     */
    public Response execute(Request request) {
        try {
            String commandName = request.getCommandName();
            AbstractCommand abstractCommand = commandMap.get(commandName);
            if (Objects.isNull(abstractCommand)) {
                throw new CommandNotFoundException(String.format("Не найдено команды: %s", commandName));
            }
            User user = null;
            if (abstractCommand.getName().equals(CommandConfiguration.HELP_NAME) && request.getToken() != null) {
                user = authService.authenticate(request.getToken());
            }
            if (abstractCommand.getCondition().equals(Condition.SECURE)) {
                user = authService.authenticate(request.getToken());
            }
            return abstractCommand.execute(request, user);
        } catch (UndeclaredThrowableException e) {
            Throwable cause = e.getCause();
            if (cause instanceof InvocationTargetException) {
                cause = ((InvocationTargetException) cause).getTargetException();
            }
            String message = cause != null ? cause.getMessage() : "Неизвестная ошибка";
            if (cause instanceof TokenTimeRuntimeException) {
                return Response.builder()
                        .answerType(AnswerType.REAUTHORIZATION)
                        .message(message)
                        .build();
            }
            return Response.builder()
                    .answerType(AnswerType.ERROR)
                    .message(message)
                    .build();
        } catch (TokenTimeRuntimeException e) {
            return Response.builder()
                    .answerType(AnswerType.REAUTHORIZATION)
                    .message(e.getMessage())
                    .build();
        } catch (Throwable e) {
            return Response.builder()
                    .answerType(AnswerType.ERROR)
                    .message(e.getMessage())
                    .build();
        }
    }
}
