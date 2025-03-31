package ru.ifmo.se.server;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.io.impl.ReaderImpl;
import ru.ifmo.se.common.io.impl.WriterImpl;
import ru.ifmo.se.server.command.*;
import ru.ifmo.se.server.command.special.AbstractSpecialCommand;
import ru.ifmo.se.server.command.special.ExitCommand;
import ru.ifmo.se.server.command.special.SaveCommand;
import ru.ifmo.se.server.command.special.SpecialCommandManager;
import ru.ifmo.se.server.database.data.Dao;
import ru.ifmo.se.server.database.data.Database;
import ru.ifmo.se.server.database.dump.DatabaseDump;
import ru.ifmo.se.server.database.dump.DatabaseDumpLoader;
import ru.ifmo.se.server.database.dump.DatabaseDumpValidator;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.exception.DumpDataBaseValidationException;
import ru.ifmo.se.server.exception.FileReadException;
import ru.ifmo.se.server.exception.NonNullException;
import ru.ifmo.se.server.receiver.Receiver;
import ru.ifmo.se.server.util.AbsolutePathResolver;
import ru.ifmo.se.server.util.NetworkService;

import ru.ifmo.se.common.io.Reader;
import ru.ifmo.se.common.io.Writer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс Starter предназначен для инициализации и запуска приложения.
 * Он управляет командами, чтением и записью данных, а также взаимодействует с базой данных.
 */
@Slf4j
public class Starter {
    /**
     * Имя переменной окружения, содержащей путь к файлу.
     */
    public final static String NAME_PATH_VARIABLE = "PATH_FILE";

    /**
     * Объект для взаимодействия с базой данных.
     */
    private Dao<LabWork> database;

    /**
     * Объект Receiver, который управляет выполнением команд.
     */
    private static Receiver receiver;

    /**
     * Объект Writer для вывода сообщений.
     */
    private static Writer writerReal;

    /**
     * Объект Reader для чтения ввода пользователя.
     */
    private static Reader consoleReader;

    /**
     * Объект CommandManager для управления командами.
     */
    private CommandManager commandManager;
    private SpecialCommandManager specialCommandManager;
    /**
     * Путь к файлу, используемый для загрузки и сохранения данных.
     */
    private static String path;

    public Starter() {
    }

    /**
     * Инициализирует команды, которые будут доступны в приложении.
     * @param writer Писатель для вывода данных.
     * @param path   Путь к файлу, используемый для команд, связанных с файлами.
     */
    private void initializeCommands(Writer writer, String path) {
        List<AbstractCommand> listCommand = List.of(new ShowCommand(receiver),
                new ClearCommand(receiver),
                new AddCommand(receiver),
                new SortCommand(receiver), new SortCommand(receiver),
                new MinByMinimalPointCommand(receiver),
                new CountGreaterThanAuthorCommand(receiver),
                new InfoCommand(receiver), new UpdateIdCommand(receiver),
                new RemoveByIdCommand(receiver),
                new InsertAtIndexCommand(receiver),
                new RemoveFirstCommand(receiver),
                new GroupCountingByMinimalPointCommand(receiver));
        List<AbstractSpecialCommand> listSpecialCommand = List.of(new ExitCommand(receiver, writer, path),
                new SaveCommand(receiver, writer, path));
        for (AbstractCommand command : listCommand) {
            commandManager.register(command.getName(), command);
        }
        for (AbstractSpecialCommand command : listSpecialCommand) {
            specialCommandManager.register(command.getName(), command);
        }
        HelpCommand help = new HelpCommand(receiver, commandManager.getDescriptionMap());
        commandManager.register(help.getName(), help);
    }

    /**
     * Обрабатывает запросы пользователя, выполняя команды, введенные через консоль.
     *
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода данных.
     */
    private void makeRequest(Reader reader, Writer writer) {
        try {
            while (true) {
                writer.print("Введите вашу команду: ");
                specialCommandManager.execute(reader.readLine());
            }
        } catch (NonNullException e) {
            writer.println(e.getMessage());
            makeRequest(reader, writer);
        }
    }

    private void makeRequestFromClient() {
        log.info("сервер поднят");
        NetworkService networkService = new NetworkService();
        networkService.run(commandManager);

    }

    private void startBothMethods(Reader reader, Writer writer) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> makeRequest(reader, writer));
    }
    /**
     * Инициализирует вспомогательные объекты, такие как писатель, читатель, менеджер команд и базу данных.
     *
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода данных.
     */
    private void launchAssistants(BufferedReader reader, BufferedWriter writer) {
        try {
            writerReal = new WriterImpl(writer);
            consoleReader = new ReaderImpl(reader);
            commandManager = new CommandManager(writerReal);
            specialCommandManager = new SpecialCommandManager(writerReal);
            DatabaseDump databaseDump = getDatabaseDump(writerReal);
            database = new Database(databaseDump);
            receiver = new Receiver(database);
        } catch (DumpDataBaseValidationException e) {
            writerReal.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Загружает дамп базы данных из файла или создает новый, если файл недоступен.
     *
     * @param writer Писатель для вывода данных.
     * @return Объект DatabaseDump, содержащий данные базы данных.
     */
    private DatabaseDump getDatabaseDump(Writer writer) {
        DatabaseDumpLoader databaseDumpLoader = new DatabaseDumpLoader();
        path = System.getenv(NAME_PATH_VARIABLE);
        AbsolutePathResolver absolutePathResolver = new AbsolutePathResolver();
        path = absolutePathResolver.resolvePath(path);
        try {
            DatabaseDump databaseDump = databaseDumpLoader.loadDatabaseDump(path);
            if (databaseDump != null) {
                DatabaseDumpValidator databaseDumpValidator = new DatabaseDumpValidator();
                databaseDumpValidator.isValidateDatabase(databaseDump);
                writer.println(String.format("успешное считывание c -> %s", path));
                return databaseDump;
            } else {
                writerReal.println(String.format("создана новая коллекция," +
                        " путь для сохранения: -> %s", path));
                return databaseDumpLoader.getDefaultDatabaseDump();
            }
        } catch (FileReadException e) {
            writerReal.println(e.getMessage());
            writerReal.println("не удалось считать данные с файла," +
                    " вы будете работать с пустой коллекцией!");
            return databaseDumpLoader.getDefaultDatabaseDump();
        }
    }

    /**
     * Запускает приложение, инициализируя необходимые компоненты и начиная обработку команд.
     */
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(System.out, StandardCharsets.UTF_8))) {
            launchAssistants(reader, writer);
            writerReal.println("введите help для получения информации о командах");
            initializeCommands(writerReal, path);
            startBothMethods(consoleReader, writerReal);
            makeRequestFromClient();
        } catch (IOException e) {
            writerReal.println("ошибка потока ввода");
        }
    }
}
