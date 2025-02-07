package se.ifmo.command;

import se.ifmo.create.CreateLabWork;
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
        CreateLabWork createLabWork = new CreateLabWork(reader, writer);
        receiver.add(createLabWork.createLabWork());
        writer.print("Успешное создание сущности LabWork\n");
    }
}