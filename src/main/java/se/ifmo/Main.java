package se.ifmo;

import se.ifmo.command.Clear;
import se.ifmo.command.CommandManager;
import se.ifmo.command.Show;
import se.ifmo.data.Database;
import se.ifmo.entity.Person;
import se.ifmo.receiver.Receiver;

public class Main {
    public static void main(String[] args) {
        Database<Person> db = new Database<>();
        Receiver<Person> receiver = new Receiver<>(db);
        CommandManager commandManager = new CommandManager();
        commandManager.register("show", new Show<Person>(receiver));
        commandManager.register("clear", new Clear(receiver));

        commandManager.execute("clear", "");
        commandManager.execute("show", "");
    }
}