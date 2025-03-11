package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.configuration.CommandConfiguration;
import se.ifmo.receiver.Receiver;

/**
 * Команда для удаления первого элемента из коллекции.
 * Если коллекция пуста, выводится сообщение об ошибке.
 */
public class RemoveFirstCommand extends WithoutParametersCommand  {

    /**
     * Конструктор команды удаления первого элемента.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     */
    public RemoveFirstCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.REMOVE_FIRST_NAME, CommandConfiguration.REMOVE_FIRST_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду удаления первого элемента из коллекции.
     * Если коллекция пуста, выводится сообщение о том, что сущности отсутствуют.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        if (receiver.getAll().isEmpty()) {
            writer.println("В коллекции нет сущностей");
        } else {
            long id = receiver.getAll().get(0).getId();
            receiver.removeById(id);
            writer.println("Успешное удаление");
        }
    }
}
