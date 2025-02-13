package se.ifmo.command;

import se.ifmo.io.JsonWriter;
import se.ifmo.io.Writer;
import se.ifmo.io.impl.JsonWriterImpl;
import se.ifmo.preset.CommandName;
import se.ifmo.preset.DatabaseDump;
import se.ifmo.receiver.Receiver;

public class SaveCommand extends WithoutParametersCommand  {

    private String path;

    public SaveCommand(Receiver receiver, Writer writer, String path) {
        super(receiver, CommandName.SAVE_NAME, CommandName.SAVE_DESCRIPTION, writer);
        this.path = path;
    }

    @Override
    public void execute(String parameter) {
        if (checkParameters(parameter, writer)) {
            return;
        }
        JsonWriter<DatabaseDump> jsonWriter = new JsonWriterImpl<>();
        jsonWriter.writeToJson(receiver.getDatabaseDump(), path);
        writer.println("успешно сохранено");
    }
}