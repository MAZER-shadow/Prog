package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class Save extends OwnCommand implements Command {

    public Save(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}