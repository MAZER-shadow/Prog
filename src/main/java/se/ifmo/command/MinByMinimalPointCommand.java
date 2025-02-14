package se.ifmo.command;

import se.ifmo.entity.LabWork;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MinByMinimalPointCommand extends WithoutParametersCommand  {

    public MinByMinimalPointCommand(Receiver receiver, Writer writer) {
        super(receiver, CommandConfiguration.MIN_BY_MINIMAL_POINT_NAME,
                CommandConfiguration.MIN_BY_MINIMAL_POINT_DESCRIPTION, writer);
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        List<LabWork> list = new ArrayList<>(receiver.getAll());
        Collections.sort(list, Comparator.comparingDouble(LabWork::getMinimalPoint));
        writer.println(list.get(0).aboutLabWork());
    }
}
