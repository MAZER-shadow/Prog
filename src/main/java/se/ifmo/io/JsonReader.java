package se.ifmo.io;

import java.util.List;

public interface JsonReader<T> {
    List<T> readJson(String path);
}