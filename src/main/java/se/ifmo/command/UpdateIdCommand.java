package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

public class UpdateIdCommand extends AbstractCommand  {

    public UpdateIdCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.UPDATE_ID_NAME, CommandName.UPDATE_ID_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {

    }
}