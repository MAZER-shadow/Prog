package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class History extends OwnCommand implements Command {

    public History(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}