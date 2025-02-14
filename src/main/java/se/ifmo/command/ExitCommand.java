package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class ExitCommand extends WithoutParametersCommand  {

    public ExitCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.EXIT_NAME, CommandConfiguration.EXIT_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        writer.println("Завершение программы");
        System.exit(0);
    }
}
