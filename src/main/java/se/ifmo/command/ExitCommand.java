package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

public class ExitCommand extends AbstractCommand  {

    public ExitCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.EXIT_NAME, CommandName.EXIT_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        writer.println("Завершение программы");
        System.exit(0);
    }
}