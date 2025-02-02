package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class Help extends OwnCommand implements Command {

    public Help(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}