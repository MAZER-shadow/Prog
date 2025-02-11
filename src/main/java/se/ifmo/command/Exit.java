package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.receiver.Receiver;

public class Exit extends OwnCommand implements Command {
    private final Writer writer;

    public Exit(Receiver receiver, Writer writer) {
        super(receiver);
        this.writer = writer;
    }

    @Override
    public void execute(String parameter) {
        writer.println("Завершение программы");
        System.exit(0);
    }
}