package se.ifmo.command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commandMap = new HashMap<>();

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    public void execute(String commandName, String parameter) {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new IllegalStateException(String.format("Не найдено команды %s", commandName));
        }
        command.execute(parameter);
    }
}