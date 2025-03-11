package se.ifmo.command;

import se.ifmo.creator.LabWorkCreator;
import se.ifmo.entity.LabWork;
import se.ifmo.entity.Person;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.configuration.CommandConfiguration;
import se.ifmo.receiver.Receiver;

/**
 * Команда для подсчёта количества элементов в коллекции, чьи авторы имеют большую
 * лексикографическую значимость, чем переданный объект Person.
 */
public class CountGreaterThanAuthorCommand extends WithoutParametersCommand  {
    private final Reader reader;
    private final boolean flag;

    /**
     * Конструктор команды подсчёта элементов с автором, лексикографически большим, чем переданный.
     *
     * @param receiver Объект для взаимодействия с базой данных.
     * @param reader Читатель для создания объектов.
     * @param writer Писатель для вывода сообщений.
     * @param flag Флаг, используемый для создания данных.
     */
    public CountGreaterThanAuthorCommand(Receiver receiver, Reader reader, Writer writer, boolean flag) {
        super(receiver, CommandConfiguration.COUNT_GREATER_THAN_AUTHOR_NAME,
                CommandConfiguration.COUNT_GREATER_THAN_AUTHOR_DESCRIPTION, writer);
        this.reader = reader;
        this.flag = flag;
    }

    /**
     * Выполняет команду подсчёта количества элементов, у которых автор лексикографически
     * больше переданного объекта Person.
     *
     * @param parameter Параметр команды (не используется в данной команде).
     */
    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        LabWorkCreator labWorkCreator = new LabWorkCreator(reader, writer , flag);
        Person person = labWorkCreator.createPerson();
        long count = receiver.getAll()
                .stream()
                .map(LabWork::getAuthor)
                .filter(element -> element.compareTo(person) > 0)
                .count();
        writer.println(String.format("Результат CountGreaterThanAuthorCommand: %s", count));
    }
}
