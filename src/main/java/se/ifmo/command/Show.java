package se.ifmo.command;

import se.ifmo.receiver.Receiver;

public class Show extends OwnCommand implements Command {

    public Show(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(String parameter) {
        for (Object person : receiver.getAll()) {
            System.out.println(person);
        }
    }
}