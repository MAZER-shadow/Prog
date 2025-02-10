package se.ifmo.preset;

import se.ifmo.entity.LabWork;
import se.ifmo.io.JsonReader;
import se.ifmo.io.Reader;
import se.ifmo.io.Writer;
import se.ifmo.io.impl.JsonReaderImpl;
import se.ifmo.io.impl.WriterImpl;
import java.io.BufferedWriter;

public class LoaderFromStartFile {
    private final Writer writer;
    private final Reader reader;

    public LoaderFromStartFile(BufferedWriter writer, Reader reader) {
        this.writer = new WriterImpl(writer);
        this.reader = reader;
    }

    public void load() {
        writer.println("Введите переменную окружения, содержащую имя файла:  ");
        String envPath = reader.readLine();
        if (envPath == null || envPath.isEmpty()) {
            return;
        }
        String fileName = System.getenv(envPath);
        if (fileName == null) {
            writer.println(String.format("Ошибка: Переменная окружения %s не установлена, попробуйте ещё раз. ", envPath));
            load();
        }
        JsonReader<LabWork> file = new JsonReaderImpl<>(LabWork.class);
        file.readJson(fileName);

    }
}