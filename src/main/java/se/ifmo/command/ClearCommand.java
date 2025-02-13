package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

public class ClearCommand extends WithoutParametersCommand  {

    public ClearCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.CLEAR_NAME, CommandName.CLEAR_DESCRIPTION, writer);
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