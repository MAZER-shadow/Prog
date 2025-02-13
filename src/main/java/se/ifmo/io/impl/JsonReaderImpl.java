package se.ifmo.io.impl;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import se.ifmo.exception.IORuntimeException;
import se.ifmo.io.JsonReader;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonReaderImpl<T> implements JsonReader<T> {
    private final Class<T> clazz;

    public JsonReaderImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T readJson(String path) throws IllegalStateException {
        try (FileInputStream inputStream = new FileInputStream(path);
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader)) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder())
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}