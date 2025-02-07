package se.ifmo.command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commandMap = new HashMap<>();

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public void execute(String input) {
        String[] parameters = input.split(" ");
        Command command = commandMap.get(parameters[0]);
        if (command == null) {
            throw new IllegalStateException(String.format("Не найдено команды %s", parameters[0]));
        }
        if (parameters.length > 1) {
            command.execute(parameters[1]);
        } else {
            command.execute("");
        }
    }
}