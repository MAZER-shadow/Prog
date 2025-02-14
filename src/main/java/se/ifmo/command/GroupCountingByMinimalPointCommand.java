package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class GroupCountingByMinimalPointCommand extends WithoutParametersCommand  {

    public GroupCountingByMinimalPointCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.GROUP_COUNTING_BY_MINIMAL_POINT_NAME,
                CommandConfiguration.GROUP_COUNTING_BY_MINIMAL_POINT_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
    }
}
