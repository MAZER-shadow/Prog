package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class MinByMinimalPoint extends OwnCommand implements Command {

    public MinByMinimalPoint(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}