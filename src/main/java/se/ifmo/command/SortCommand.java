package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

import java.util.Collections;
import java.util.List;

public class SortCommand extends AbstractCommand  {

    public SortCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.SORT_NAME, CommandName.SORT_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        List<LabWork> list = receiver.getAll();
        Collections.sort(list);
        for (LabWork obj : list) {
            writer.println(obj.aboutLabWork());
        }
    }
}