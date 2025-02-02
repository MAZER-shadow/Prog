package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class RemoveLower extends OwnCommand implements Command {

    public RemoveLower(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}