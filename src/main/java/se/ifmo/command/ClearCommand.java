package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class ClearCommand extends WithoutParametersCommand  {

    public ClearCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.CLEAR_NAME, CommandConfiguration.CLEAR_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        receiver.clear();
        writer.println("Коллекция очищена");
    }
}
