package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

public class ExecuteScriptCommand extends AbstractCommand  {

    public ExecuteScriptCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.EXECUTE_SCRIPT_NAME, CommandName.EXECUTE_SCRIPT_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {

    }
}
