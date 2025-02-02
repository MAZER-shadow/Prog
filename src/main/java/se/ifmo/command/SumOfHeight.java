package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class SumOfHeight extends OwnCommand implements Command {

    public SumOfHeight(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}