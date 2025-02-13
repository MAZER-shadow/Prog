package se.ifmo.io;

public interface JsonReader<T> {
    T readJson(String path) throws IllegalStateException;
}