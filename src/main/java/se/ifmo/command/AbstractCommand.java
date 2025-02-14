package se.ifmo.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import se.ifmo.io.Writer;
import se.ifmo.receiver.Receiver;
@RequiredArgsConstructor
public abstract class AbstractCommand {
    @NonNull
    protected Receiver receiver;
    @Getter
    protected final String name;
    @Getter
    protected final String description;
    protected final Writer writer;

    public abstract void execute(String parameters);
}
