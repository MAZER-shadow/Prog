package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

public class InfoCommand extends AbstractCommand  {

    public InfoCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.INFO_NAME, CommandName.INFO_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
    }
}