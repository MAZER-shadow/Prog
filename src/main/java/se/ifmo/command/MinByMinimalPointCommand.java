package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MinByMinimalPointCommand extends AbstractCommand  {

    public MinByMinimalPointCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandName.MIN_BY_MINIMAL_POINT_NAME, CommandName.MIN_BY_MINIMAL_POINT_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        List<LabWork> list = new ArrayList<>(receiver.getAll());
        Collections.sort(list, Comparator.comparingDouble(LabWork::getMinimalPoint));
        writer.println(list.get(0).aboutLabWork());
    }
}