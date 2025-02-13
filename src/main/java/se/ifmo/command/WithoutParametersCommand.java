package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.receiver.Receiver;

public abstract class WithoutParametersCommand extends AbstractCommand {
    public WithoutParametersCommand(Receiver receiver, String name, String description, Writer writer) {
        super(receiver, name, description, writer);
    }

    protected boolean checkParameters(String parameter, Writer writer) {
        if (!parameter.isEmpty()) {
            writer.println(String.format("%s не нуждается в параметре", getName()));
            return true;
        }
        return false;
    }
}