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
    private String path;

    private void initializeCommands(Reader reader, Writer writer, String path) {
        ShowCommand show = new ShowCommand(receiver, writer);
        ClearCommand clear = new ClearCommand(receiver, writer);
        AddCommand add = new AddCommand(receiver, reader, writer);
        ExitCommand exit = new ExitCommand(receiver, writer);
        SaveCommand save = new SaveCommand(receiver, writer, path);
        SortCommand sort = new SortCommand(receiver, writer);
        MinByMinimalPointCommand minByMinimalPoint = new MinByMinimalPointCommand(receiver, writer);
        CountGreaterThanAuthorCommand countGreaterThanAuthorCommand =
                new CountGreaterThanAuthorCommand(receiver, reader, writer);
        commandManager.register(show.getName(), show);
        commandManager.register(clear.getName(), clear);
        commandManager.register(add.getName(), add);
        commandManager.register(exit.getName(), exit);
        commandManager.register(save.getName(), save);
        commandManager.register(sort.getName(), sort);
        commandManager.register(minByMinimalPoint.getName(), minByMinimalPoint);
        commandManager.register(countGreaterThanAuthorCommand.getName(), countGreaterThanAuthorCommand);
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
        /*ЗАМЕНИ ВХОДНЫЕ ДАННЫЕ*/
        DatabaseDump databaseDump = getDatabaseDump(writerReal);
        database = new Database(databaseDump);
        receiver = new Receiver(database);
    }

    private DatabaseDump getDatabaseDump(Writer writer) {
        //String path = System.getenv("PATH_FILE");
        //JsonReader<DatabaseDump> jsonReader = new JsonReaderImpl<>(DatabaseDump.class);
        FirstDatabaseDumpGetter firstDatabaseDumpGetter = new FirstDatabaseDumpGetter(writer);
        DatabaseDump databaseDump = firstDatabaseDumpGetter.getDatabaseDump();
        path = firstDatabaseDumpGetter.getPath();
        return databaseDump;
        //return jsonReader.readJson(path);
        //валидации чтения
        //задана ли переменная PATH_FILE -> сказать об этом и дать путь по которому будет сохранение
        //есть ли файл по пути этой переменной if no -> создать по пути этой переменной
        //можем ли прочитать(права) если нет сказать об этом и выйти
        //читаемость json -> то делаю метаданные по дефолту
        //валидация полей !всех!
        //починить сериализацию даты(LocalDate)
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