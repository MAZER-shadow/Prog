package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class PrintAscending extends OwnCommand implements Command {

    public PrintAscending(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
    }
}