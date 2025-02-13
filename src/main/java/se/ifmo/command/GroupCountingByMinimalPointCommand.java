package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

public class GroupCountingByMinimalPointCommand extends AbstractCommand  {

    public GroupCountingByMinimalPointCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.GROUP_COUNTING_BY_MINIMAL_POINT_NAME,
                CommandName.GROUP_COUNTING_BY_MINIMAL_POINT_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
    }
}