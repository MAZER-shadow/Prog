package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class Exit extends OwnCommand implements Command {

    public Exit(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
        //System.out.println("Завершение программы");
        //System.exit(0);
    }
}