package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.configuration.CommandConfiguration;
import se.ifmo.receiver.Receiver;

/**
 * Класс ShowCommand предназначен для вывода всех элементов коллекции LabWork.
 * Наследуется от WithoutParametersCommand.
 */
public class ShowCommand extends WithoutParametersCommand  {

    /**
     * Конструктор ShowCommand.
     *
     * @param receiver объект, управляющий коллекцией.
     * @param writer объект для вывода информации пользователю.
     */
    public ShowCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.SHOW_NAME, CommandConfiguration.SHOW_DESCRIPTION, writer);
    }

    /**
     * Выполняет вывод всех элементов коллекции LabWork.
     * Если коллекция пуста, выводит соответствующее сообщение.
     *
     * @param parameter параметр команды (не используется).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        if (receiver.getSize() == 0) {
            writer.println("Нет элементов в коллекции");
            return;
        }
        for (LabWork labWork: receiver.getAll()) {
            writer.println(labWork.aboutLabWork());
        }
    }
}
