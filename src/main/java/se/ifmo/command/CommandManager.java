package org.example.command;

import java.util.HashMap;

public class CommandManager {
    private HashMap<String, Command> commandMap = new HashMap<>();

    public void register(String CommandName, Command command) {
        commandMap.put(CommandName, command);
    }

    public void execute(String commandName) {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new IllegalStateException(String.format("Не найдено команды %s", commandName));
        }
        command.execute();
    }
}
