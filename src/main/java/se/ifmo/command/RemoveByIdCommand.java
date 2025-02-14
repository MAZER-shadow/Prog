package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class RemoveByIdCommand extends AbstractCommand  {

    public RemoveByIdCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.REMOVE_BY_ID_NAME, CommandConfiguration.REMOVE_BY_ID_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
    }
}
