package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.receiver.Receiver;

/**
 * Абстрактный класс для команд, которые требуют параметров для выполнения.
 * Наследуется от {@link AbstractCommand} и реализует проверку наличия параметров.
 */
public abstract class WithParametersCommand extends AbstractCommand {
    /**
     * Конструктор для команды, требующей параметров.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param name Название команды.
     * @param description Описание команды.
     * @param writer Писатель для вывода сообщений.
     */
    public WithParametersCommand(Receiver receiver, String name, String description, Writer writer) {
        super(receiver, name, description, writer);
    }

    /**
     * Проверяет наличие параметра для команды.
     * Если параметр отсутствует, выводится сообщение об ошибке.
     *
     * @param parameter Параметр команды.
     * @param writer Писатель для вывода сообщений.
     * @return true, если параметр пустой, иначе false.
     */
    protected boolean checkParameters(String parameter, Writer writer) {
        if (parameter.isEmpty()) {
            writer.println(String.format("%s нуждается в параметре", getName()));
            return true;
        }
        return false;
    }
}
