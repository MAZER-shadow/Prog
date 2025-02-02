package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class FilterLess extends OwnCommand implements Command {

    public FilterLess(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}