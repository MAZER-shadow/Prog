package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class CountGreaterThanAuthor extends OwnCommand implements Command {

    public CountGreaterThanAuthor(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}