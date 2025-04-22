package ru.ifmo.se.client.command;

import ru.ifmo.se.client.Starter;
import ru.ifmo.se.client.configuration.CommandConfiguration;
import ru.ifmo.se.client.exception.ExecuteScriptException;
import ru.ifmo.se.client.exception.NonNullScriptException;
import ru.ifmo.se.client.util.AbsolutePathResolver;
import ru.ifmo.se.client.util.PathValidator;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.exception.NonNullException;
import ru.ifmo.se.common.io.Writer;

import java.util.HashSet;
import java.util.Set;

/**
 * Команда для выполнения скрипта из указанного файла.
 * Читает команды из файла и выполняет их, предотвращая повторное выполнение того же файла.
 */
public class ExecuteScriptCommand extends WithParametersCommand {
    private static final Set<String> SET_OF_PATH = new HashSet<>();

    /**
     * Конструктор команды выполнения скрипта.
     *
     * @param writer Писатель для вывода сообщений.
     */
    public ExecuteScriptCommand(Writer writer) {
        super(CommandConfiguration.EXECUTE_SCRIPT_NAME,
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
    public Request makeRequest(String parameter) {
        verifyParameter(parameter);
        AbsolutePathResolver resolver = new AbsolutePathResolver();
        String path = resolver.resolvePath(parameter);
        PathValidator validator = new PathValidator(writer);
        if (!validator.isValidPathToRead(path)) {
            throw new ExecuteScriptException();
        }
        if (SET_OF_PATH.contains(path)) {
            writer.println(String.format("по этому пути -> %s запрещено выполнение," +
                    " файл уже был запущен в скрипте", path));
            throw new ExecuteScriptException();
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
        return null;
    }

    @Override
    public void handleResponse(Response response) {

    }
}
