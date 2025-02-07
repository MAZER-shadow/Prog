package se.ifmo;

import se.ifmo.command.Add;
import se.ifmo.command.Clear;
import se.ifmo.command.CommandManager;
import se.ifmo.command.Show;
import se.ifmo.data.Database;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.io.impl.ConsoleReader;
import se.ifmo.io.impl.WriterImpl;
import se.ifmo.receiver.Receiver;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
                /*BufferedWriter writerVoid = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("/dev/null")))*/) {

            Database db = new Database();
            Receiver receiver = new Receiver(db);
            CommandManager commandManager = new CommandManager();
            Reader consoleReader = new ConsoleReader(reader);
            Writer writer3 = new WriterImpl(writer);
            
            commandManager.register("show", new Show(receiver));
            commandManager.register("clear", new Clear(receiver));
            commandManager.register("add", new Add(receiver, consoleReader, writer3));

            while (true) {
                writer3.print("Введите вашу команду: ");
                commandManager.execute(consoleReader.readLine());
            }
        }
    }
}