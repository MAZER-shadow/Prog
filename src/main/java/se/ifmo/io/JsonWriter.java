package se.ifmo.io;

import java.util.List;

public interface JsonWriter<T> {
    void writeToJson(List<T> list, String path);
}