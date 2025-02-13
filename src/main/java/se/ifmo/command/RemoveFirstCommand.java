package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

public class RemoveFirstCommand extends AbstractCommand  {

    public RemoveFirstCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.REMOVE_FIRST_NAME, CommandName.REMOVE_FIRST_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
    }
}