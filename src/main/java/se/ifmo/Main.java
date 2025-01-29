package se.ifmo;

import se.ifmo.command.Add;
import se.ifmo.command.CommandManager;
import se.ifmo.command.Show;

public class Main {
    public static void main(String[] args) {
        CommandManager commandManager = new CommandManager();
        commandManager.register("add", new Add());
        commandManager.register("show", new Show());
        commandManager.execute("add", "David");
        commandManager.execute("show", "");
    }
}