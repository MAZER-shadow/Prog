package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class ExecuteScript extends OwnCommand implements Command {

    public ExecuteScript(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {

    }
}
