package se.ifmo.command;

import se.ifmo.create.LabWorkCreator;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

public class AddCommand extends WithoutParametersCommand  {
    private final Reader reader;

    public AddCommand(Receiver receiver, Reader reader, Writer writer) {
        super(receiver, CommandConfiguration.ADD_NAME, CommandConfiguration.ADD_DESCRIPTION, writer);
        this.reader = reader;
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        LabWorkCreator creator = new LabWorkCreator(reader, writer);
        receiver.add(creator.createLabWork());
        writer.println(String.format("Успешное создание сущности LabWork, id = %d", receiver.getMaxId()));
    }
}
