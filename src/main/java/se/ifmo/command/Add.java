package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class Add extends OwnCommand implements Command {

    public Add(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}