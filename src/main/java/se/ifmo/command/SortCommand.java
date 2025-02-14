package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

import java.util.Collections;
import java.util.List;

public class SortCommand extends WithoutParametersCommand  {

    public SortCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.SORT_NAME, CommandConfiguration.SORT_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        if (receiver.getSize() == 0) {
            writer.println("Нет элементов в коллекции -> нечего сортировать");
            return;
        }
        List<LabWork> list = receiver.getAll();
        Collections.sort(list);
        for (LabWork obj : list) {
            writer.println(obj.aboutLabWork());
        }
    }
}
