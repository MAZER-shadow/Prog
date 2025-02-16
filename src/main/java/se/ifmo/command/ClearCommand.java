package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

/**
 * Команда для очистки всей коллекции.
 * Удаляет все элементы из коллекции, делая её пустой.
 */
public class ClearCommand extends WithoutParametersCommand  {

    /**
     * Конструктор команды очистки коллекции.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param writer Писатель для вывода сообщений.
     */
    public ClearCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.CLEAR_NAME, CommandConfiguration.CLEAR_DESCRIPTION, writer);
    }

    /**
     * Выполняет команду очистки коллекции, удаляя все элементы.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        receiver.clear();
        writer.println("Коллекция очищена");
    }
}
