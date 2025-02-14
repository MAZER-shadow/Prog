package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class InfoCommand extends WithoutParametersCommand  {

    public InfoCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.INFO_NAME, CommandConfiguration.INFO_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
    }
}
