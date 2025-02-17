package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.configuration.CommandConfiguration;
import se.ifmo.receiver.Receiver;

import java.util.Collections;
import java.util.List;

/**
 * Класс SortCommand предназначен для сортировки коллекции LabWork
 * в естественном порядке и вывода её элементов.
 * Наследуется от WithoutParametersCommand.
 */
public class SortCommand extends WithoutParametersCommand  {

    /**
     * Конструктор SortCommand.
     *
     * @param receiver объект, управляющий коллекцией.
     * @param writer объект для вывода информации пользователю.
     */
    public SortCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.SORT_NAME, CommandConfiguration.SORT_DESCRIPTION, writer);
    }

    /**
     * Выполняет сортировку коллекции LabWork и выводит её элементы.
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
            writer.println("Нет элементов в коллекции -> нечего сортировать");
            return;
        }
        List<LabWork> list = receiver.getAll();
        Collections.sort(list);
        for (LabWork obj : list) {
            writer.println(obj.aboutLabWork());
        }
    }
}
