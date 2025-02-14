package se.ifmo.command;

import se.ifmo.exception.CommandNotFoundException;
import se.ifmo.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandManager {
    private static final String SEPARATOR = " ";
    private final Map<String, AbstractCommand> commandMap = new HashMap<>();
    private Writer writer;
    private static final int POSITION_COMMAND_NAME_IN_INPUT = 0;
    private static final int POSITION_ARGUMENT_OF_COMMAND_IN_INPUT = 1;
    private static final int SUM_WORDS_OF_COMMAND_NAME_AND_ARGUMENTS = 2;
    private static final String DEFAULT_ARGUMENT_OF_COMMAND = "";

    public CommandManager(Writer writer) {
        this.writer = writer;
    }

    public void register(String abstractCommandName, AbstractCommand abstractCommand) {
        commandMap.put(abstractCommandName, abstractCommand);
    }

    public void execute(String input) {
        try {
            String[] parameters = input.split(SEPARATOR);
            String abstractCommandName = parameters[POSITION_COMMAND_NAME_IN_INPUT];
            AbstractCommand abstractCommand = commandMap.get(abstractCommandName);
            if (Objects.isNull(abstractCommand) || parameters.length > SUM_WORDS_OF_COMMAND_NAME_AND_ARGUMENTS) {
                throw new CommandNotFoundException("Не найдено такой команды");
            }
            if (parameters.length == SUM_WORDS_OF_COMMAND_NAME_AND_ARGUMENTS) {
                abstractCommand.execute(parameters[POSITION_ARGUMENT_OF_COMMAND_IN_INPUT]);
            } else {
                abstractCommand.execute(DEFAULT_ARGUMENT_OF_COMMAND);
            }
        } catch (CommandNotFoundException e) {
            writer.println(e.getMessage());
        }
    }

    public Map<String, String> getDescriptionMap() {
        Map<String, String> descriptionAbstractCommand = new HashMap<>();
        for (AbstractCommand value : commandMap.values()) {
            descriptionAbstractCommand.put(value.getName(), value.getDescription());
        }
        return descriptionAbstractCommand;
    }
}
