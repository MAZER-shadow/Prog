package se.ifmo.command;

import se.ifmo.create.LabWorkCreator;
import se.ifmo.entity.LabWork;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

import java.time.LocalDate;

/**
 * Команда для вставки новой сущности в коллекцию по указанному индексу.
 * Если индекс больше, чем размер коллекции, выводится сообщение об ошибке.
 */
public class InsertAtIndexCommand extends WithParametersCommand  {
    private final LabWorkCreator creator;
    private final int flag;

    /**
     * Конструктор команды вставки новой сущности по индексу.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param reader Читатель для ввода данных.
     * @param writer Писатель для вывода сообщений.
     * @param flag Флаг для определения дополнительной логики создания сущности.
     */
    public InsertAtIndexCommand(Receiver receiver, Reader reader, Writer writer, int flag) {
        super(receiver, CommandConfiguration.INSERT_AT_INDEX_NAME,
                CommandConfiguration.INSERT_AT_INDEX_DESCRIPTION, writer);
        creator = new LabWorkCreator(reader, writer, flag);
        this.flag = flag;
    }

    /**
     * Выполняет команду вставки новой сущности в коллекцию по указанному индексу.
     * Если индекс больше, чем размер коллекции, выводится сообщение об ошибке.
     * Вставленная сущность получает уникальный id и текущую дату создания.
     *
     * @param parameter Параметр команды — индекс, на который нужно вставить сущность.
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        try {
            Long id = Long.parseLong(parameter);
            if (receiver.getAll().size() < id) {
                writer.println("в коллекции меньше элементов чем передаваемый индекс");
            } else {
                LabWork labWork = creator.createLabWork();
                labWork.setId(receiver.getMaxId() + 1);
                labWork.setCreationDate(LocalDate.now());
                receiver.getAll().add(Math.toIntExact(id), labWork);
                writer.println("успешно вставлен");
            }
        } catch (NumberFormatException e) {
            writer.println("Значение id не в том формате");
        }
    }
}
