package se.ifmo.command;

import se.ifmo.create.LabWorkCreator;
import se.ifmo.entity.LabWork;
import se.ifmo.entity.Person;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class CountGreaterThanAuthorCommand extends WithoutParametersCommand  {
    private final Reader reader;

    public CountGreaterThanAuthorCommand(Receiver receiver, Reader reader, Writer writer) {
        super(receiver, CommandConfiguration.COUNT_GREATER_THAN_AUTHOR_NAME,
                CommandConfiguration.COUNT_GREATER_THAN_AUTHOR_DESCRIPTION, writer);
        this.reader = reader;
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        LabWorkCreator labWorkCreator = new LabWorkCreator(reader, writer);
        Person person = labWorkCreator.createPerson();
        long count = receiver.getAll()
                .stream()
                .map(LabWork::getAuthor)
                .filter(element -> element.compareTo(person) > 0)
                .count();
        writer.println(String.format("Результат: %s", count));
    }
}
