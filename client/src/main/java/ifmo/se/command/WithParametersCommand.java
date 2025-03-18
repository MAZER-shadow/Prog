package ifmo.se.command;

import ifmo.se.io.Writer;

import javax.sound.midi.Receiver;

/**
 * Абстрактный класс для команд, которые требуют параметров для выполнения.
 * Наследуется от {@link AbstractCommand} и реализует проверку наличия параметров.
 */
public abstract class WithParametersCommand extends AbstractCommand {
    /**
     * Конструктор для команды, требующей параметров.
     *
     * @param name Название команды.
     * @param description Описание команды.
     * @param writer Писатель для вывода сообщений.
     */
    public WithParametersCommand(String name, String description, Writer writer) {
        super(name, description, writer);
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
        return !parameter.isEmpty();
//        if (parameter.isEmpty()) {
//            writer.println(String.format("%s нуждается в параметре", getName()));
//            return true;
//        }
//        return false;
    }
}
