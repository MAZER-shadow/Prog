package se.ifmo.command;

import se.ifmo.create.LabWorkCreator;
import se.ifmo.entity.LabWork;
import se.ifmo.entity.Person;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

public class CountGreaterThanAuthorCommand extends AbstractCommand  {
    private final Reader reader;

    public CountGreaterThanAuthorCommand(Receiver receiver, Reader reader, Writer writer) {
        super(receiver, CommandName.COUNT_GREATER_THAN_AUTHOR_NAME,
                CommandName.COUNT_GREATER_THAN_AUTHOR_DESCRIPTION, writer);
        this.reader = reader;
    }

    @Override
    public void execute(String parameter) {
        LabWorkCreator labWorkCreator = new LabWorkCreator(reader, writer);
        Person person = labWorkCreator.createPerson();
//        int s = 0;
//        for (LabWork labWork : receiver.getAll()) {
//            if (labWork.getAuthor().compareTo(person) > 0) {
//                s+=1;
//            }
//        };
        long count = receiver.getAll()
                .stream()
                .map(LabWork::getAuthor)
                .filter(element -> element.compareTo(person) > 0)
                .count();
        writer.println(String.valueOf(count));
    }
}