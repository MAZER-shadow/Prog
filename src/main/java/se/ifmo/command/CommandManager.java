package se.ifmo.command;

import se.ifmo.exception.IORuntimeException;
import se.ifmo.io.Writer;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final String SEPARATOR = " ";
    private final Map<String, Command> commandMap = new HashMap<>();
    private Writer writer;

    public CommandManager(Writer writer) {
        this.writer = writer;
    }

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public void execute(String input) {
        try {
            String[] parameters = input.split(SEPARATOR);
            Command command = commandMap.get(parameters[0]);
            if (command == null || parameters.length > 2) {
                throw new IORuntimeException("Не найдено такой команды");
            }
            if (parameters.length == 2) {
                command.execute(parameters[1]);
            } else {
                command.execute("");
            }
        } catch (IORuntimeException e) {
            writer.println(e.getMessage());
        }
    }
}