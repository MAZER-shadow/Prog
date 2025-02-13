package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

public class InsertAtIndexCommand extends AbstractCommand  {

    public InsertAtIndexCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.INSERT_AT_INDEX_NAME, CommandName.INSERT_AT_INDEX_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
    }
}