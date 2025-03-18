package ifmo.se;

import ifmo.se.command.*;
import ifmo.se.database.data.Dao;
import ifmo.se.database.data.Database;
import ifmo.se.database.dump.DatabaseDump;
import ifmo.se.database.dump.DatabaseDumpLoader;
import ifmo.se.database.dump.DatabaseDumpValidator;
import ifmo.se.entity.LabWork;
import ifmo.se.exception.DumpDataBaseValidationException;
import ifmo.se.exception.FileReadException;
import ifmo.se.exception.NonNullException;
import ifmo.se.io.Reader;
import ifmo.se.io.Writer;
import ifmo.se.io.impl.ReaderImpl;
import ifmo.se.io.impl.WriterImpl;
import ifmo.se.receiver.Receiver;
import ifmo.se.request.AbstractRequest;
import ifmo.se.response.AbstractResponse;
import ifmo.se.util.AbsolutePathResolver;
import ifmo.se.util.NetWorkService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Класс Starter предназначен для инициализации и запуска приложения.
 * Он управляет командами, чтением и записью данных, а также взаимодействует с базой данных.
 */
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
        List<AbstractCommand> listCommand = List.of(
                new AddCommand(receiver, reader, writer, flag));
    }

    /**
     * Обрабатывает запросы пользователя, выполняя команды, введенные через консоль.
     *
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода данных.
     */
    private void makeRequest(Reader reader, Writer writer) {
        try {
            System.out.println("подняли сервак");
            NetWorkService netWorkService = new NetWorkService();
            while (true) {
                System.out.println("ждём шляпу");
                AbstractRequest abstractRequest = netWorkService.getRequest();
                System.out.println("пришла шляпа");
                AbstractResponse abstractResponse = commandManager.execute(abstractRequest.getNameCommand(), abstractRequest);
                netWorkService.sendResponse(abstractResponse);
                System.out.println("ушла шляпа");
            }
        } catch (NonNullException e) {
            writer.println(e.getMessage());
            makeRequest(reader, writer);
        }
    }

//    private void makeRequestForRequestFromFile(Reader reader) {
//        while (true) {
//            String line = reader.readLine();
//            commandManager.execute(line);
//        }
//    }

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
//                makeRequestForRequestFromFile(readerCommandFromFile);
            } catch (IOException e) {
                writerReal.println("ошибка потока ввода");
            }
        }
    }
}
