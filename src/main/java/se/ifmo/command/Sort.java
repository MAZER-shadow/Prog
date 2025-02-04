package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class Sort extends OwnCommand implements Command {

    public Sort(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}