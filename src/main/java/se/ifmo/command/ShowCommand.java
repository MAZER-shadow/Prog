package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class ShowCommand extends WithoutParametersCommand  {

    public ShowCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.SHOW_NAME, CommandConfiguration.SHOW_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        if (receiver.getSize() == 0) {
            writer.println("Нет элементов в коллекции");
            return;
        }
        for (LabWork labWork: receiver.getAll()) {
            writer.println(labWork.aboutLabWork());
        }
    }
}
