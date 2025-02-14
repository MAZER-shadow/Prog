package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class RemoveFirstCommand extends WithoutParametersCommand  {

    public RemoveFirstCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.REMOVE_FIRST_NAME, CommandConfiguration.REMOVE_FIRST_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
    }
}
