package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class GroupCountingByMinimalPoint extends OwnCommand implements Command {

    public GroupCountingByMinimalPoint(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}