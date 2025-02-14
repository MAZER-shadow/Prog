package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class UpdateIdCommand extends AbstractCommand  {

    public UpdateIdCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.UPDATE_ID_NAME, CommandConfiguration.UPDATE_ID_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {

    }
}
