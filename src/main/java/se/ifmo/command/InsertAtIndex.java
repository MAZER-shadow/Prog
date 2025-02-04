package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class InsertAtIndex extends OwnCommand implements Command {

    public InsertAtIndex(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}