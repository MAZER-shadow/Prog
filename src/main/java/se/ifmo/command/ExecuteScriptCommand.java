package se.ifmo.command;

import se.ifmo.exception.NonNullException;
import se.ifmo.exception.NonNullScriptException;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.io.impl.ReaderImpl;
import se.ifmo.preset.AbsolutePathResolver;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Команда для выполнения скрипта из указанного файла.
 * Читает команды из файла и выполняет их, предотвращая повторное выполнение того же файла.
 */
public class ExecuteScriptCommand extends WithParametersCommand  {
    private static Set<String> listOfPath = new HashSet<>();
    private final String pathToSave;
    private CommandManager commandManager;

    /**
     * Конструктор команды выполнения скрипта.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     * @param pathToSave Путь для сохранения данных.
     */
    public ExecuteScriptCommand(Receiver receiver, Writer writer, String pathToSave) {
        super(receiver, CommandConfiguration.EXECUTE_SCRIPT_NAME,
                CommandConfiguration.EXECUTE_SCRIPT_DESCRIPTION, writer);
        this.pathToSave = pathToSave;
    }

    /**
     * Выполняет команду исполнения скрипта из файла.
     * Проверяет корректность пути и предотвращает повторное выполнение скрипта.
     * Выполняет все команды, прописанные в скрипте, и выводит результат.
     *
     * @param parameter Параметр команды, содержащий путь к файлу со скриптом.
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        AbsolutePathResolver resolver = new AbsolutePathResolver();
        String path = resolver.resolvePath(parameter);
        if (!isValidPath(path)) {
            return;
        }
        if (listOfPath.contains(path)) {
            writer.println(String.format("по этому пути -> %s запрещено выполнение," +
                    " файл уже был запущен в скрипте", path));
            return;
        }
        listOfPath.add(path);
        try (FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader)) {
            Reader readerCommandFromFile = new ReaderImpl(reader);
            commandManager = new CommandManager(writer);
            initializeCommands(readerCommandFromFile, writer, pathToSave);
            makeRequest(readerCommandFromFile);
            writer.println(String.format("закончено выполнение скрипта: %s", path));
            listOfPath.clear();
        } catch (IOException e) {
            writer.println("ошибка потока ввода");
            listOfPath.clear();
        } catch (NonNullScriptException | NonNullException e) {
            writer.println(String.format("закончено выполнение скрипта: %s", path));
            listOfPath.clear();
        }
    }

    /**
     * Читает и выполняет команды из переданного объекта Reader.
     * Выполняет каждую команду по очереди, пока не достигнут конец файла.
     *
     * @param reader Объект для чтения команд из файла.
     */
    private void makeRequest(Reader reader) {
        String line = "";
        while (line != null) {
            line = reader.readLine();
            commandManager.execute(line);
        }
    }

    /**
     * Проверяет, является ли указанный путь к файлу валидным.
     * Путь должен быть существующим файлом с правами на чтение.
     *
     * @param path Путь к файлу.
     * @return true, если путь валиден, иначе false.
     */
    private boolean isValidPath(String path) {
        File pFile = new File(path);
        if (pFile.getParent() == null) {
            writer.println(String.format("Это не файл -> %s", pFile));
            return false;
        }
        File pDir = new File(pFile.getParent().toString());
        if (!isValidDirectoryPath(pDir)) {
            return false;
        }
        if (!pFile.isFile()) {
            writer.println(String.format("Это не файл -> %s", pFile));
            return false;
        }
        if (!pFile.canRead()) {
            writer.println(String.format("Ошибка доступа чтения у -> %s", path));
            return false;
        }
        return true;
    }

    /**
     * Проверяет, является ли указанный путь к директории валидным.
     * Директория должна существовать и иметь права на чтение.
     *
     * @param pDir Директория.
     * @return true, если директория валидна, иначе false.
     */
    private boolean isValidDirectoryPath(File pDir) {
        if (!pDir.isDirectory()) {
            writer.println(String.format("Это не директория -> %s", pDir));
            return false;
        }
        if (!pDir.canRead()) {
            writer.println(String.format("Ошибка доступа чтения у -> %s", pDir));
            return false;
        }
        return true;
    }

    /**
     * Инициализирует доступные команды для выполнения в рамках скрипта.
     *
     * @param reader Объект для чтения данных команд.
     * @param writer Писатель для вывода сообщений.
     * @param path Путь для сохранения данных.
     */
    private void initializeCommands(Reader reader, Writer writer, String path) {
        List<AbstractCommand> listCommand = List.of(new ShowCommand(receiver, writer),
                new ClearCommand(receiver, writer),
                new AddCommand(receiver, reader, writer, 0),
                new ExitCommand(receiver, writer), new SaveCommand(receiver, writer, path),
                new SortCommand(receiver, writer), new SortCommand(receiver, writer),
                new MinByMinimalPointCommand(receiver, writer),
                new CountGreaterThanAuthorCommand(receiver, reader, writer, 0),
                new InfoCommand(receiver, writer), new UpdateIdCommand(receiver, reader, writer, 0),
                new RemoveByIdCommand(receiver, writer),
                new InsertAtIndexCommand(receiver, reader, writer, 0),
                new RemoveFirstCommand(receiver, writer),
                new GroupCountingByMinimalPointCommand(receiver, writer),
                new ExecuteScriptCommand(receiver, writer, path));
        for (AbstractCommand command : listCommand) {
            commandManager.register(command.getName(), command);
        }
        HelpCommand help = new HelpCommand(receiver, writer, commandManager.getDescriptionMap());
        commandManager.register(help.getName(), help);
    }
}
