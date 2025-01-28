package se.ifmo.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ReaderFile<T> implements JsonReader<T> {
    private final Type listType;

    public ReaderFile(Class<T> clazz) {
        this.listType = TypeToken.getParameterized(List.class, clazz).getType();
    }

    @Override
    public List<T> readJson(String path) {
        try (InputStream inputStream = getResourceAsStream(path);
                InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getResourceAsStream(String resource) {
        InputStream classLoader = getClass().getClassLoader().getResourceAsStream(resource);
        if (classLoader == null) {
            throw new IllegalArgumentException(String.format("Файл, %s, не найден", resource));
        } else {
            return classLoader;
        }
    }
}