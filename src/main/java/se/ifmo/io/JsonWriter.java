package se.ifmo.io;

public interface JsonWriter<T> {
    void writeToJson(T objeect, String path);
}