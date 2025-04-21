package ru.ifmo.se.client.command;

import ru.ifmo.se.common.exception.IORuntimeException;
import ru.ifmo.se.common.io.Writer;

/**
 * Абстрактный класс для команд, которые не требуют параметров для выполнения.
 * Наследуется от {@link AbstractCommand} и реализует проверку наличия параметров.
 */
public abstract class WithoutParametersCommand extends AbstractCommand {
    /**
     * Конструктор для команды, не требующей параметров.
     *
     * @param name        Название команды.
     * @param description Описание команды.
     * @param writer      Писатель для вывода сообщений.
     */
    public WithoutParametersCommand(String name, String description, Writer writer) {
        super(name, description, writer);
    }

    /**
     * Проверяет наличие параметра для команды.
     * Если параметр передан, выводится сообщение об ошибке.
     *
     * @param parameter Параметр команды.
     * @return true, если параметр присутствует, иначе false.
     */
    protected void verifyParameter(String parameter) {
        if (!parameter.isEmpty()) {
            throw new IORuntimeException(String.format("%s не нуждается в параметре", getName()));
        }
    }
}
