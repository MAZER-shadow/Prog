package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class ExecuteScriptCommand extends AbstractCommand  {

    public ExecuteScriptCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.EXECUTE_SCRIPT_NAME,
                CommandConfiguration.EXECUTE_SCRIPT_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {

    }
}
