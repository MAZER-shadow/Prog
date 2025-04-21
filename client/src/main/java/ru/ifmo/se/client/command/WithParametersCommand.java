package ru.ifmo.se.client.command;

import ru.ifmo.se.common.exception.IORuntimeException;
import ru.ifmo.se.common.io.Writer;

/**
 * Абстрактный класс для команд, которые требуют параметров для выполнения.
 * Наследуется от {@link AbstractCommand} и реализует проверку наличия параметров.
 */
public abstract class WithParametersCommand extends AbstractCommand {
    /**
     * Конструктор для команды, требующей параметров.
     *
     * @param name        Название команды.
     * @param description Описание команды.
     * @param writer      Писатель для вывода сообщений.
     */
    public WithParametersCommand(String name, String description, Writer writer) {
        super(name, description, writer);
    }

    /**
     * Проверяет наличие параметра для команды.
     * Если параметр отсутствует, выводится сообщение об ошибке.
     *
     * @param parameter Параметр команды.
     * @return true, если параметр пустой, иначе false.
     */
    protected void verifyParameter(String parameter) {
        if (parameter.isEmpty()) {
            throw new IORuntimeException(String.format("%s нуждается в параметре", getName()));
        }
    }
}
