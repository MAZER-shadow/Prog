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
import java.util.List;

public class Starter {
    public final static String NAME_PATH_VARIABLE = "PATH_FILE";
    private Dao<LabWork> database;
    private Receiver receiver;
    private Writer writerReal;
    private Reader consoleReader;
    private CommandManager commandManager;
    private String path;

    private void initializeCommands(Reader reader, Writer writer, String path) {
        List<AbstractCommand> listCommand = List.of(new ShowCommand(receiver, writer),
                new ClearCommand(receiver, writer),
                new AddCommand(receiver, reader, writer),
                new ExitCommand(receiver, writer), new SaveCommand(receiver, writer, path),
                new SortCommand(receiver, writer), new SortCommand(receiver, writer),
                new MinByMinimalPointCommand(receiver, writer),
                new CountGreaterThanAuthorCommand(receiver, reader, writer));
        for (AbstractCommand command : listCommand) {
            commandManager.register(command.getName(), command);
        }
        HelpCommand help = new HelpCommand(receiver, writer, commandManager.getDescriptionMap());
        commandManager.register(help.getName(), help);
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
        commandManager = new CommandManager(writerReal);
        DatabaseDump databaseDump = getDatabaseDump(writerReal);
        database = new Database(databaseDump);
        receiver = new Receiver(database);
    }

    private DatabaseDump getDatabaseDump(Writer writer) {
        path = System.getenv(NAME_PATH_VARIABLE);
        DatabaseDumpLoader databaseDumpLoader = new DatabaseDumpLoader(writer, path);
        DatabaseDump databaseDump = databaseDumpLoader.exportDatabaseDump();
        path = databaseDumpLoader.getPath();
        return databaseDump;
        //валидации записи
        //проверить путь, есть ли -> то сказать об этом выдать путь куда я сохранил.
        //нет прав на запись
//        DatabaseMetaData databaseMetaData = new DatabaseMetaData();
//        databaseMetaData.setClazz("LabWork");
//        databaseMetaData.setLocalDateTime("24.05.2006");
//        DatabaseDump databaseDump = new DatabaseDump(databaseMetaData, new ArrayList<>());
//        return databaseDump;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
                /*BufferedWriter writerVoid = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("/dev/null")))*/) {
            launchAssistants(reader, writer);
            initializeCommands(consoleReader, writerReal, path);
            makeRequest(consoleReader, writerReal);
        } catch (IOException e) {
            writerReal.println("ошибка потока ввода");
        }
    }
}
