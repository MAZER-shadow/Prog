package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class RemoveGreater extends OwnCommand implements Command {

    public RemoveGreater(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}