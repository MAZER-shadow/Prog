package se.ifmo.preset.impl;

import se.ifmo.command.*;
import se.ifmo.data.Dao;
import se.ifmo.data.Database;
import se.ifmo.entity.LabWork;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.io.impl.ConsoleReader;
import se.ifmo.io.impl.WriterImpl;
import se.ifmo.preset.LoaderFromStartFile;
import se.ifmo.preset.Starter;
import se.ifmo.receiver.Receiver;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class StarterImpl implements Starter {
    private final Dao<LabWork> database;
    private final Receiver receiver;
    private CommandManager commandManager;

    public StarterImpl() {
        database = new Database();
        receiver = new Receiver(database);
    }

    private void addCommands(Reader reader, Writer writer) {
        commandManager.register("show", new Show(receiver));
        commandManager.register("clear", new Clear(receiver));
        commandManager.register("add", new Add(receiver, reader, writer));
        commandManager.register("exit", new Exit(receiver));
    }

    private void addRequest(Reader reader, Writer writer) {
        while (true) {
            writer.print("Введите вашу команду: ");
            commandManager.execute(reader.readLine());
        }
    }

    public void run() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
                /*BufferedWriter writerVoid = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("/dev/null")))*/) {
            Reader consoleReader = new ConsoleReader(reader);
            Writer writerReal = new WriterImpl(writer);
            this.commandManager = new CommandManager(writerReal);
            addCommands(consoleReader, writerReal);
            LoaderFromStartFile loader = new LoaderFromStartFile(writer, consoleReader);
            loader.load();
            addRequest(consoleReader, writerReal);
        }
    }
}