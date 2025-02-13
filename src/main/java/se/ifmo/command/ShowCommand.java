package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

public class ShowCommand extends AbstractCommand  {

    public ShowCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.SHOW_NAME, CommandName.SHOW_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        if (receiver.getSize() == 0) {
            writer.println("Нет элементов в коллекции");
            return;
        }
        for (LabWork labWork: receiver.getAll()) {
            writer.println(labWork.aboutLabWork());
        }
    }
}