package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class Add implements Command {
    @Override
    public void execute(String parameter) {
        Receiver receiver = new Receiver();
        receiver.add(parameter);
    }
}