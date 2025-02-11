package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.receiver.Receiver;

public class Show extends OwnCommand implements Command {
    private final Writer writer;

    public Show(Receiver receiver, Writer writer) {
        super(receiver);
        this.writer = writer;
    }

    @Override
    public void execute(String parameter) {
        if (receiver.getSize() == 0) {
            writer.println("Нет элементов в коллекции");
            return;
        }
        for (LabWork labWork: receiver.getAll()) {
            writer.println(labWork.aboutLabWork());
        }
    }
}