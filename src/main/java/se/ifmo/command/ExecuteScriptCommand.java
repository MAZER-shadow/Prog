package se.ifmo.command;

import se.ifmo.Starter;
import se.ifmo.exception.NonNullException;
import se.ifmo.exception.NonNullScriptException;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.io.impl.ReaderImpl;
import se.ifmo.util.AbsolutePathResolver;
import se.ifmo.configuration.CommandConfiguration;
import se.ifmo.receiver.Receiver;
import se.ifmo.util.PathValidator;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Команда для выполнения скрипта из указанного файла.
 * Читает команды из файла и выполняет их, предотвращая повторное выполнение того же файла.
 */
public class ExecuteScriptCommand extends WithParametersCommand  {
    private static final Set<String> setOfPath = new HashSet<>();

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
        if (setOfPath.contains(path)) {
            writer.println(String.format("по этому пути -> %s запрещено выполнение," +
                    " файл уже был запущен в скрипте", path));
            return;
        }
        setOfPath.add(path);
        try {
            Starter starter = new Starter(path);
            starter.run();
        } catch (NonNullScriptException | NonNullException e) {
            writer.println(String.format("закончено выполнение скрипта: %s", path));
            setOfPath.clear();
        }
    }
}
