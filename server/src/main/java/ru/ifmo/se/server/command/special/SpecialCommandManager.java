package ru.ifmo.se.server.command.special;

import ru.ifmo.se.common.io.Writer;
import ru.ifmo.se.server.exception.CommandNotFoundException;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpecialCommandManager {
    private static final String SEPARATOR = " ";

    /**
     * Карта для хранения зарегистрированных команд.
     */
    private final Map<String, AbstractSpecialCommand> commandMap = new HashMap<>();

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
    public SpecialCommandManager(Writer writer) {
        this.writer = writer;
    }

    /**
     * Регистрирует команду в карте команд.
     *
     * @param abstractCommandName Имя команды.
     * @param abstractCommand Объект команды, который будет зарегистрирован.
     */
    public void register(String abstractCommandName, AbstractSpecialCommand abstractCommand) {
        commandMap.put(abstractCommandName, abstractCommand);
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
            String abstractCommandName = parameters[POSITION_COMMAND_NAME_IN_INPUT];
            AbstractSpecialCommand abstractSpecialCommand = commandMap.get(abstractCommandName);
            if (parameters.length > SUM_WORDS_OF_COMMAND_NAME_AND_ARGUMENTS) {
                throw new CommandNotFoundException("Слишком много параметров -_-");
            }
            if (Objects.isNull(abstractSpecialCommand)) {
                throw new CommandNotFoundException(String.format("Не найдено команды: %s", abstractCommandName));
            }
            if (parameters.length == SUM_WORDS_OF_COMMAND_NAME_AND_ARGUMENTS) {
                abstractSpecialCommand.execute(parameters[POSITION_ARGUMENT_OF_COMMAND_IN_INPUT]);
            } else {
                abstractSpecialCommand.execute(DEFAULT_ARGUMENT_OF_COMMAND);
            }
        } catch (CommandNotFoundException e) {
            writer.println(e.getMessage());
        }
    }

    /**
     * Возвращает карту с описаниями всех зарегистрированных команд.
     *
     * @return Карта, где ключ - имя команды, а значение - её описание.
     */
    public Map<String, String> getDescriptionMap() {
        Map<String, String> descriptionAbstractCommand = new HashMap<>();
        for (AbstractSpecialCommand value : commandMap.values()) {
            descriptionAbstractCommand.put(value.getName(), value.getDescription());
        }
        return descriptionAbstractCommand;
    }
}
