package se.ifmo.command;

import lombok.Getter;
import se.ifmo.io.Writer;
import se.ifmo.receiver.Receiver;

public abstract class AbstractCommand {
    protected Receiver receiver;
    @Getter
    protected final String name;
    @Getter
    protected final String description;
    protected final Writer writer;

    public AbstractCommand(Receiver receiver, String name, String description, Writer writer) {
        this.receiver = receiver;
        this.name = name;
        this.description = description;
        this.writer = writer;
    }

    public abstract void execute(String parameters);
}