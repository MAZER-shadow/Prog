package ru.ifmo.se.server.command;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Writer;
import ru.ifmo.se.common.exception.CommandNotFoundException;


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

    /**
     * Конструктор класса CommandManager. Инициализирует объект с указанным Writer.
     *
     * @param writer Объект Writer для вывода сообщений.
     */
    public CommandManager(Writer writer) {
        this.writer = writer;
    }

    /**
     * Регистрирует команду в карте команд.
     *
     * @param abstractCommandName Имя команды.
     * @param abstractCommand Объект команды, который будет зарегистрирован.
     */
    public void register(String abstractCommandName, AbstractCommand abstractCommand) {
        commandMap.put(abstractCommandName, abstractCommand);
    }

    /**
     * Выполняет команду на основе введенных данных.

     * @throws CommandNotFoundException Если команда не найдена.
     */
    public Response execute(Request request) {
        try {
            String commandName = request.getCommandName();
            AbstractCommand abstractCommand = commandMap.get(commandName);
            if (Objects.isNull(abstractCommand)) {
                throw new CommandNotFoundException(String.format("Не найдено команды: %s", commandName));
            }
            return abstractCommand.execute(request);
        } catch (CommandNotFoundException e) {
            return Response.builder()
                    .status(false)
                    .message(e.getMessage())
                    .build();
        }
    }

    /**
     * Возвращает карту с описаниями всех зарегистрированных команд.
     *
     * @return Карта, где ключ - имя команды, а значение - её описание.
     */
    public Map<String, String> getDescriptionMap() {
        Map<String, String> descriptionAbstractCommand = new HashMap<>();
        for (AbstractCommand value : commandMap.values()) {
            descriptionAbstractCommand.put(value.getName(), value.getDescription());
        }
        return descriptionAbstractCommand;
    }
}
