package ifmo.se;

import ifmo.se.command.*;

import ifmo.se.exception.CommandNotFoundException;
import ifmo.se.exception.DumpDataBaseValidationException;
import ifmo.se.exception.NonNullException;
import ifmo.se.exception.ParametrBrokeException;
import ifmo.se.io.Reader;
import ifmo.se.io.Writer;
import ifmo.se.io.impl.ReaderImpl;
import ifmo.se.io.impl.WriterImpl;
import ifmo.se.request.AbstractRequest;
import ifmo.se.response.AbstractResponse;
import ifmo.se.util.NetworkService;

import java.io.*;
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
     * Объект Receiver, который управляет выполнением команд.
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

    /**
     * Путь к файлу, используемый для загрузки и сохранения данных.
     */
    private static String path;
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
     * @param path   Путь к файлу, используемый для команд, связанных с файлами.
     */
    private void initializeCommands(Reader reader, Writer writer, String path, boolean flag) {
        List<AbstractCommand> listCommand = List.of(new AddCommand(reader, writer, flag));

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
                String command = reader.readLine();
                AbstractRequest request = commandManager.execute(command);
                NetworkService networkService = new NetworkService(12345, "127.0.0.1");
                AbstractResponse response = networkService.send(request);
                commandManager.handleResponse(response, command);
            }
        } catch (NonNullException | ParametrBrokeException | CommandNotFoundException e) {
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
            writerReal = new WriterImpl(writer);
            consoleReader = new ReaderImpl(reader);
            commandManager = new CommandManager(writerReal);
        } catch (DumpDataBaseValidationException e) {
            writerReal.println(e.getMessage());
            System.exit(1);
        }
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
                initializeCommands(consoleReader, writerReal, path, true);
                makeRequest(consoleReader, writerReal);
            } catch (IOException e) {
                writerReal.println("ошибка потока ввода");
            }
        } else {
            try (FileReader fileReader = new FileReader(pathForExecuteScript, StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(fileReader)) {
                Reader readerCommandFromFile = new ReaderImpl(reader);
                commandManager = new CommandManager(writerReal);
                initializeCommands(readerCommandFromFile, writerReal, path, false);
                makeRequestForRequestFromFile(readerCommandFromFile);
            } catch (IOException e) {
                writerReal.println("ошибка потока ввода");
            }
        }
    }
}
