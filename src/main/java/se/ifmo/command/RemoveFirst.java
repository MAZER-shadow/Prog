package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class RemoveFirst extends OwnCommand implements Command {

    public RemoveFirst(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}