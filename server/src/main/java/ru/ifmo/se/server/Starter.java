package ru.ifmo.se.server;

import io.jsonwebtoken.security.Keys;
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
import ru.ifmo.se.server.dao.UserDao;
import ru.ifmo.se.server.dao.impl.CoordinatesDaoImpl;
import ru.ifmo.se.server.dao.impl.LabWorkDaoImpl;
import ru.ifmo.se.server.dao.impl.PersonDaoImpl;
import ru.ifmo.se.server.dao.impl.UserDaoImpl;
import ru.ifmo.se.server.exception.DumpDataBaseValidationException;
import ru.ifmo.se.server.service.*;
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

    private static AuthService authService;

    private static UserService userService;
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
                new RegistrationCommand(authService),
                new AuthorizationCommand(authService),
                new AddCommand(labWorkService),
                new SortCommand(labWorkService), new SortCommand(labWorkService),
                new MinByMinimalPointCommand(labWorkService),
                new CountGreaterThanAuthorCommand(labWorkService),
                new UpdateIdCommand(labWorkService),
                new RemoveByIdCommand(labWorkService),
                new RemoveFirstCommand(labWorkService),
                new GroupCountingByMinimalPointCommand(labWorkService));
        for (AbstractCommand command : listCommand) {
            commandManager.register(command.getName(), command);
        }
        HelpCommand help = new HelpCommand(labWorkService, listCommand);
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
            ConnectionPull connectionPull = new ConnectionPull(10);
            TransactionManager transactionManager = new TransactionManager();
            LabWorkDao labWorkDao = new LabWorkDaoImpl(new CoordinatesDaoImpl(connectionPull),
                    new PersonDaoImpl(connectionPull), connectionPull);
            LabWorkService receiver = new LabWorkServiceImpl(labWorkDao);
            labWorkService = TransactionalProxy.newProxyInstance(receiver,
                    transactionManager, connectionPull, LabWorkService.class);
            UserDao userDao = new UserDaoImpl(connectionPull);
            userService = TransactionalProxy.newProxyInstance(new UserServiceImpl(userDao), transactionManager, connectionPull, UserService.class);
            AuthService authServiceLocal = new AuthServiceImpl(userService, Keys.hmacShaKeyFor(
                    ("sdfgsdfgsdfgsdfsdfsdfsdffghkfgh;lkfg;lhf'gl;hfl;gh" +
                            "fgl;hfgl;hklfgmhlkfgmhkl;fmghklmfghklmfgh" +
                            "dfghflgkmhkflgmhkfgmhlfgmhmklfgmhl;fgh" +
                            "fghfg;lh,f;lgh,f;gl,hf;lg,h").getBytes(StandardCharsets.UTF_8)));
            authService = TransactionalProxy.newProxyInstance(authServiceLocal, transactionManager, connectionPull, AuthService.class);
            commandManager = new CommandManager(writerReal, authService);

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
