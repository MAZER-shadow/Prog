package ru.ifmo.se.server;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.common.io.impl.ReaderImpl;
import ru.ifmo.se.common.io.impl.WriterImpl;
import ru.ifmo.se.server.annotationprocces.TransactionManager;
import ru.ifmo.se.server.annotationprocces.TransactionalProxy;
import ru.ifmo.se.server.command.*;
import ru.ifmo.se.server.database.ConnectionPull;
import ru.ifmo.se.server.database.Dao;
import ru.ifmo.se.server.database.LabWorkDao;
import ru.ifmo.se.server.database.impl.CoordinatesDao;
import ru.ifmo.se.server.database.impl.LabWorkDaoImpl;
import ru.ifmo.se.server.database.impl.PersonDao;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.exception.DumpDataBaseValidationException;
import ru.ifmo.se.server.exception.NonNullException;
import ru.ifmo.se.server.receiver.ReceiverImpl;
import ru.ifmo.se.server.receiver.Receiver;
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
                new UpdateIdCommand(receiver),
                new RemoveByIdCommand(receiver),
                new InsertAtIndexCommand(receiver),
                new RemoveFirstCommand(receiver),
                new GroupCountingByMinimalPointCommand(receiver));
        for (AbstractCommand command : listCommand) {
            commandManager.register(command.getName(), command);
        }
        HelpCommand help = new HelpCommand(receiver, commandManager.getDescriptionMap());
        commandManager.register(help.getName(), help);
    }


    private void makeRequestFromClient() {
        NetworkService networkService = new NetworkService();
        networkService.run(commandManager);
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
            ConnectionPull connectionPull = new ConnectionPull(10 );
            TransactionManager transactionManager = new TransactionManager();
            LabWorkDao labWorkDao = new LabWorkDaoImpl(new CoordinatesDao(connectionPull), new PersonDao(connectionPull), connectionPull);
            Receiver receiver = new ReceiverImpl(labWorkDao);
            Receiver labWorkService = TransactionalProxy.newProxyInstance(receiver, transactionManager, connectionPull, Receiver.class);
            this.receiver = labWorkService;
        } catch (DumpDataBaseValidationException e) {
            writerReal.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Запускает приложение, инициализируя необходимые компоненты и начиная обработку команд.
     */
    @SneakyThrows
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(System.out, StandardCharsets.UTF_8))) {
            log.info("сервер поднят");
            launchAssistants(reader, writer);
            writerReal.println("введите help для получения информации о командах");
            initializeCommands(writerReal, path);
            makeRequestFromClient();
            while (true) {
                Thread.sleep(Long.MAX_VALUE);
            }
        } catch (IOException e) {
            writerReal.println("ошибка потока ввода");
        }
    }
}
