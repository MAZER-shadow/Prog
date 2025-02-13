package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandName;
import se.ifmo.receiver.Receiver;

import java.util.Map;

public class HelpCommand extends AbstractCommand  {
    private final Map<String, String> descriptionMap;

    public HelpCommand(Receiver receiver, Writer writer, Map<String, String> descriptionMap) {
        super(receiver, CommandName.HELP_NAME, CommandName.HELP_DESCRIPTION, writer);

        this.descriptionMap = descriptionMap;
        descriptionMap.put(name, description);
    }

    @Override
    public void execute(String parameter) {
        for (Map.Entry<String, String> stringStringEntry : descriptionMap.entrySet()) {
            writer.println(stringStringEntry.getKey() + ": " + stringStringEntry.getValue());
        }
    }
}