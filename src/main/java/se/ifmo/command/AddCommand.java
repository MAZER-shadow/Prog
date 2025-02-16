package se.ifmo.command;

import se.ifmo.create.LabWorkCreator;
import se.ifmo.entity.LabWork;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Создаёт новый объект `LabWork` и добавляет его в коллекцию.
 */
public class AddCommand extends WithoutParametersCommand  {
    private final Reader reader;
    private final LabWorkCreator creator;

    /**
     * Конструктор команды добавления нового элемента в коллекцию.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода сообщений.
     * @param flag Флаг, используемый для настройки создания сущности.
     */
    public AddCommand(Receiver receiver, Reader reader, Writer writer, int flag) {
        super(receiver, CommandConfiguration.ADD_NAME, CommandConfiguration.ADD_DESCRIPTION, writer);
        this.reader = reader;
        creator = new LabWorkCreator(reader, writer, flag);
    }

    /**
     * Выполняет команду добавления нового элемента в коллекцию.
     * Создаёт новый объект `LabWork` и добавляет его в коллекцию.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        LabWork labWork = receiver.add(creator.createLabWork());
        writer.println(String.format("Успешное создание сущности LabWork, id = %d", labWork.getId()));
    }
}
