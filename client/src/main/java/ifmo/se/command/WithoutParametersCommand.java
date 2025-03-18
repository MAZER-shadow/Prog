package ifmo.se.command;

import ifmo.se.io.Writer;

/**
 * Абстрактный класс для команд, которые не требуют параметров для выполнения.
 * Наследуется от {@link AbstractCommand} и реализует проверку наличия параметров.
 */
public abstract class WithoutParametersCommand extends AbstractCommand {
    /**
     * Конструктор для команды, не требующей параметров.
     *
     * @param name Название команды.
     * @param description Описание команды.
     * @param writer Писатель для вывода сообщений.
     */
    public WithoutParametersCommand(String name, String description, Writer writer) {
        super(name, description, writer);
    }

    /**
     * Проверяет наличие параметра для команды.
     * Если параметр передан, выводится сообщение об ошибке.
     *
     * @param parameter Параметр команды.
     * @param writer Писатель для вывода сообщений.
     * @return false, если параметр присутствует, иначе true.
     */
    protected boolean checkParameters(String parameter, Writer writer) {
        return parameter.isEmpty();
    }
}
