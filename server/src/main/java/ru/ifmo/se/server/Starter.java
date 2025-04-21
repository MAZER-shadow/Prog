package ru.ifmo.se.server;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.annotationproccesor.TransactionManager;
import ru.ifmo.se.annotationproccesor.TransactionalProxy;
import ru.ifmo.se.common.io.Writer;
import ru.ifmo.se.common.io.impl.WriterImpl;
import ru.ifmo.se.database.ConnectionPull;
import ru.ifmo.se.server.controller.CommandManager;
import ru.ifmo.se.server.controller.command.*;
import ru.ifmo.se.server.dao.LabWorkDao;
import ru.ifmo.se.server.dao.impl.CoordinatesDao;
import ru.ifmo.se.server.dao.impl.LabWorkDaoImpl;
import ru.ifmo.se.server.dao.impl.PersonDao;
import ru.ifmo.se.server.exception.DumpDataBaseValidationException;
import ru.ifmo.se.server.service.LabWorkService;
import ru.ifmo.se.server.service.LabWorkServiceImpl;
import ru.ifmo.se.server.service.NetworkService;
import ru.ifmo.se.server.util.BannerPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Класс Starter предназначен для инициализации и запуска приложения.
 * Он управляет командами, чтением и записью данных, а также взаимодействует с базой данных.
 */
@Slf4j
public class Starter {

    /**
     * Объект Receiver, который управляет выполнением команд.
     */
    private static LabWorkService labWorkService;

    /**
     * Объект Writer для вывода сообщений.
     */
    private static Writer writerReal;

    /**
     * Объект CommandManager для управления командами.
     */
    private CommandManager commandManager;

    public Starter() {
    }

    private void initializeCommands() {
        List<AbstractCommand> listCommand = List.of(new ShowCommand(labWorkService),
                new ClearCommand(labWorkService),
                new AddCommand(labWorkService),
                new SortCommand(labWorkService), new SortCommand(labWorkService),
                new MinByMinimalPointCommand(labWorkService),
                new CountGreaterThanAuthorCommand(labWorkService),
                new UpdateIdCommand(labWorkService),
                new RemoveByIdCommand(labWorkService),
                new InsertAtIndexCommand(labWorkService),
                new RemoveFirstCommand(labWorkService),
                new GroupCountingByMinimalPointCommand(labWorkService));
        for (AbstractCommand command : listCommand) {
            commandManager.register(command.getName(), command);
        }
        HelpCommand help = new HelpCommand(labWorkService, commandManager.getDescriptionMap());
        commandManager.register(help.getName(), help);
    }

    private void makeRequestFromClient() {
        NetworkService networkService = new NetworkService();
        networkService.run(commandManager);
    }

    /**
     * Инициализирует вспомогательные объекты, такие как писатель, читатель, менеджер команд и базу данных.
     *
     * @param writer Писатель для вывода данных.
     */
    private void launchAssistants(BufferedWriter writer) {
        try {
            writerReal = new WriterImpl(writer);
            commandManager = new CommandManager(writerReal);
            ConnectionPull connectionPull = new ConnectionPull(10);
            TransactionManager transactionManager = new TransactionManager();
            LabWorkDao labWorkDao = new LabWorkDaoImpl(new CoordinatesDao(connectionPull),
                    new PersonDao(connectionPull), connectionPull);
            LabWorkService receiver = new LabWorkServiceImpl(labWorkDao);
            LabWorkService labWorkService = TransactionalProxy.newProxyInstance(receiver,
                    transactionManager, connectionPull, LabWorkService.class);
            this.labWorkService = labWorkService;
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
        BannerPrinter.printBanner();
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(System.out, StandardCharsets.UTF_8))) {
            launchAssistants(writer);
            initializeCommands();
            makeRequestFromClient();
            log.info("сервер готов к работе");
            while (true) {
                Thread.sleep(Long.MAX_VALUE);
            }
        } catch (IOException e) {
            writerReal.println("ошибка потока ввода");
        }
    }
}
