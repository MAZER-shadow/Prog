package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class InsertAtIndexCommand extends AbstractCommand  {

    public InsertAtIndexCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.INSERT_AT_INDEX_NAME,
                CommandConfiguration.INSERT_AT_INDEX_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
    }
}
