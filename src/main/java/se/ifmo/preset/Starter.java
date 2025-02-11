package se.ifmo.preset;

import se.ifmo.command.*;
import se.ifmo.data.Dao;
import se.ifmo.data.Database;
import se.ifmo.entity.LabWork;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.io.impl.ReaderImpl;
import se.ifmo.io.impl.WriterImpl;
import se.ifmo.receiver.Receiver;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Starter {
    private Dao<LabWork> database;
    private Receiver receiver;
    private Writer writerReal;
    private Reader consoleReader;
    private CommandManager commandManager;

    private void initializationCommands(Reader reader, Writer writer) {
        commandManager.register("show", new Show(receiver, writer));
        commandManager.register("clear", new Clear(receiver));
        commandManager.register("add", new Add(receiver, reader, writer));
        commandManager.register("exit", new Exit(receiver, writer));
    }

    private void makeRequest(Reader reader, Writer writer) {
        while (true) {
            writer.print("Введите вашу команду: ");
            commandManager.execute(reader.readLine());
        }
    }

    private void launchAssistants(BufferedReader reader, BufferedWriter writer) {
        writerReal = new WriterImpl(writer);
        consoleReader = new ReaderImpl(reader);
        this.commandManager = new CommandManager(writerReal);
        //тут надо будет прописать логику если есть уже сущности то их в DB
        //если нет, то создаём пустую.
        database = new Database();
        receiver = new Receiver(database);
    }

    public void run() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
                /*BufferedWriter writerVoid = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("/dev/null")))*/) {
            launchAssistants(reader, writer);
            initializationCommands(consoleReader, writerReal);
            makeRequest(consoleReader, writerReal);
        }
    }
}