package se.ifmo.command;

import se.ifmo.Starter;
import se.ifmo.configuration.CommandConfiguration;
import se.ifmo.exception.NonNullException;
import se.ifmo.exception.NonNullScriptException;
import se.ifmo.io.Writer;
import se.ifmo.receiver.Receiver;
import se.ifmo.util.AbsolutePathResolver;
import se.ifmo.util.PathValidator;

import java.util.HashSet;
import java.util.Set;

/**
 * Команда для выполнения скрипта из указанного файла.
 * Читает команды из файла и выполняет их, предотвращая повторное выполнение того же файла.
 */
public class ExecuteScriptCommand extends WithParametersCommand  {
    private static final Set<String> SET_OF_PATH = new HashSet<>();

    /**
     * Конструктор команды выполнения скрипта.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     */
    public ExecuteScriptCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.EXECUTE_SCRIPT_NAME,
                CommandConfiguration.EXECUTE_SCRIPT_DESCRIPTION, writer);
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
        PathValidator validator = new PathValidator(writer);
        if (!validator.isValidPathToRead(path)) {
            return;
        }
        if (SET_OF_PATH.contains(path)) {
            writer.println(String.format("по этому пути -> %s запрещено выполнение," +
                    " файл уже был запущен в скрипте", path));
            return;
        }
        SET_OF_PATH.add(path);
        try {
            Starter starter = new Starter(path);
            starter.run();
        } catch (NonNullScriptException e) {
            writer.println(String.format("закончено неуспешно выполнение скрипта: %s", path));
            SET_OF_PATH.clear();
        } catch (NonNullException e) {
            writer.println(String.format("закончено успешно выполнение скрипта: %s", path));
            SET_OF_PATH.clear();
        }
    }
}
