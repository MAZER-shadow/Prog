package se.ifmo.command;

import se.ifmo.io.Writer;
import se.ifmo.preset.CommandConfiguration;
import se.ifmo.receiver.Receiver;

import java.util.Map;

public class HelpCommand extends WithoutParametersCommand  {
    private final StringBuilder resultCommand;

    public HelpCommand(Receiver receiver, Writer writer, Map<String, String> descriptionMap) {
        super(receiver, CommandConfiguration.HELP_NAME, CommandConfiguration.HELP_DESCRIPTION, writer);
        resultCommand = new StringBuilder();
        descriptionMap.put(name, description);
        for (Map.Entry<String, String> stringStringEntry : descriptionMap.entrySet()) {
            resultCommand.append(stringStringEntry.getKey()).append(": ")
                    .append(stringStringEntry.getValue()).append("\n");
        }
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        writer.println(resultCommand.toString());
    }
}
