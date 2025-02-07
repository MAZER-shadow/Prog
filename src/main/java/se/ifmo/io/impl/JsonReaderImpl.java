package se.ifmo.io.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import se.ifmo.io.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonReaderImpl<T> implements JsonReader<T> {
    private final Type listType;

    public JsonReaderImpl(Class<T> clazz) {
        this.listType = TypeToken.getParameterized(List.class, clazz).getType();
    }

    @Override
    public List<T> readJson(String path) {
        try (FileInputStream inputStream = new FileInputStream(path);
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}