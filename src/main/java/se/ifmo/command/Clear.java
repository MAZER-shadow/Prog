package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class Clear extends OwnCommand implements Command {

    public Clear(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
        receiver.clear();
    }
}