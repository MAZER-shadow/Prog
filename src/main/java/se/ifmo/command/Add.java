package se.ifmo.command;

import se.ifmo.create.LabWorkCreator;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.receiver.Receiver;

public class Add extends OwnCommand implements Command {
    private final Reader reader;
    private final Writer writer;

    public Add(Receiver receiver, Reader reader, Writer writer ) {
        super(receiver);
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void execute(String parameter) {
        if (!parameter.isEmpty()) {
            writer.println("add, не нуждается в параметре" );
            return;
        }
        LabWorkCreator creator = new LabWorkCreator(reader, writer);
        receiver.add(creator.createLabWork());
        writer.println("Успешное создание сущности LabWork");
    }
}