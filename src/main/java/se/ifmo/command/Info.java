package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class Info extends OwnCommand implements Command {

    public Info(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}