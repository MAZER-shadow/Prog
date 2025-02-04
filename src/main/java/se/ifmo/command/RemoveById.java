package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class RemoveById extends OwnCommand implements Command {

    public RemoveById(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}
