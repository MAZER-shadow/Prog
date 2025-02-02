package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class UpdateId extends OwnCommand implements Command {

    public UpdateId(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}