package se.ifmo.io.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import se.ifmo.io.JsonWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonWriterImpl<T> implements JsonWriter<T> {
    @Override
    public void writeToJson(List<T> list, String path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
                OutputStreamWriter streamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                BufferedWriter writer = new BufferedWriter(streamWriter)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}