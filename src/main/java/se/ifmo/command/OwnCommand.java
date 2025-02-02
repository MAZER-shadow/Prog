package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public abstract class OwnCommand {
    protected Receiver receiver;

    public OwnCommand(Receiver receiver) {
        this.receiver = receiver;
    }
}