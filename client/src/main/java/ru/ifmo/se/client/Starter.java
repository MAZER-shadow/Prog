package ru.ifmo.se.client;


import ru.ifmo.se.client.command.*;
import ru.ifmo.se.client.service.NetworkService;
import ru.ifmo.se.common.exception.NonNullException;
import ru.ifmo.se.common.io.impl.ReaderImpl;
import ru.ifmo.se.common.io.impl.WriterImpl;

import ru.ifmo.se.common.io.Writer;
import ru.ifmo.se.common.io.Reader;

import java.io.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * Класс Starter предназначен для инициализации и запуска приложения.
 * Он управляет командами, чтением и записью данных, а также взаимодействует с базой данных.
 */
public class Starter {

    /**
     * Объект для взаимодействия с базой данных.
     */


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

    private static NetworkService networkService;

    private static String ip;
    private static Integer port;

    private boolean flag;
    private String pathForExecuteScript;

    public Starter() {
        flag = true;
    }

    public Starter(String pathForExecuteScript) {
        this.flag = false;
        this.pathForExecuteScript = pathForExecuteScript;
    }

    /**
     * Инициализирует команды, которые будут доступны в приложении.
     *
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода данных.
     */
    private void initializeCommands(Reader reader, Writer writer, boolean flag) {
        List<AbstractCommand> listCommand = List.of(new ShowCommand(writer),
                new ClearCommand(writer),
                new AddCommand(reader, writer, flag),
                new SortCommand(writer), new SortCommand(writer),
                new MinByMinimalPointCommand(writer),
                new CountGreaterThanAuthorCommand(reader, writer, flag),
                new InfoCommand(writer), new UpdateIdCommand(reader, writer, flag),
                new RemoveByIdCommand(writer),
                new InsertAtIndexCommand(reader, writer, flag),
                new RemoveFirstCommand(writer), new HelpCommand(writer),
                new GroupCountingByMinimalPointCommand(writer),
                new ExecuteScriptCommand(writer));
        for (AbstractCommand command : listCommand) {
            commandManager.register(command.getName(), command);
        }
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
                commandManager.execute(reader.readLine());
            }
        } catch (NonNullException e) {
            writer.println(e.getMessage());
            makeRequest(reader, writer);
        }
    }

    private void makeRequestForRequestFromFile(Reader reader) {
        while (true) {
            String line = reader.readLine();
            commandManager.execute(line);
        }
    }

    /**
     * Инициализирует вспомогательные объекты, такие как писатель, читатель, менеджер команд и базу данных.
     *
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода данных.
     */
    private void launchAssistants(BufferedReader reader, BufferedWriter writer) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            writerReal = new WriterImpl(writer);
            consoleReader = new ReaderImpl(reader);
            requestIp();
            requestPort();
            networkService = new NetworkService(ip, port, channel, selector);
            commandManager = new CommandManager(writerReal, networkService);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void requestPort() {
        writerReal.println("Введите порт");
        try {
            String reader = consoleReader.readLine();
            if (!isValidPort(reader)) {
                writerReal.println("не корректный port");
                requestPort();
            } else {
                port = Integer.parseInt(reader);
            }
        } catch (NonNullException e) {
            writerReal.println(e.getMessage());
            requestPort();
        }
    }

    private boolean isValidPort(String stringPort) {
        try {
            int portNumber = Integer.parseInt(stringPort);
            if (portNumber >= 0 && portNumber <= 65535) {
                port = portNumber;
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void requestIp() {
        writerReal.println("Введите ip");
        try {
            ip = consoleReader.readLine();
        } catch (NonNullException e) {
            writerReal.println(e.getMessage());
            requestIp();
        }
        if (!isValidIpAddress(ip)) {
            writerReal.println("не корректный ip");
            requestIp();
        }
    }

    private boolean isValidIpAddress(String ip) {
        String ipv4Pattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        String ipv6Pattern = "^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$";

        return ip.matches(ipv4Pattern) || ip.matches(ipv6Pattern);
    }
    /**
     * Запускает приложение, инициализируя необходимые компоненты и начиная обработку команд.
     */
    public void run() {
        if (flag) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                    BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(System.out, StandardCharsets.UTF_8))) {
                launchAssistants(reader, writer);
                writerReal.println("введите help для получения информации о командах");
                initializeCommands(consoleReader, writerReal, true);
                makeRequest(consoleReader, writerReal);
            } catch (IOException e) {
                writerReal.println("ошибка потока ввода");
            }
        } else {
            try (FileReader fileReader = new FileReader(pathForExecuteScript, StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(fileReader)) {
                Reader readerCommandFromFile = new ReaderImpl(reader);
                commandManager = new CommandManager(writerReal, networkService);
                initializeCommands(readerCommandFromFile, writerReal, false);
                makeRequestForRequestFromFile(readerCommandFromFile);
            } catch (IOException e) {
                writerReal.println("ошибка потока ввода");
            }
        }
    }
}
