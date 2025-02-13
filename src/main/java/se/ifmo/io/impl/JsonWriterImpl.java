package se.ifmo.io.impl;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import se.ifmo.exception.IORuntimeException;
import se.ifmo.io.JsonWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonWriterImpl<T> implements JsonWriter<T> {
    @Override
    public void writeToJson(T object, String path) {
        Gson gson = Converters.registerLocalDate(new GsonBuilder())
                .setPrettyPrinting()
                .create();

        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
                OutputStreamWriter streamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                BufferedWriter writer = new BufferedWriter(streamWriter)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}