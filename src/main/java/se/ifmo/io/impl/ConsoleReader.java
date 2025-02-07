package se.ifmo.io.impl;

import se.ifmo.exception.IORuntimeException;
import se.ifmo.io.Reader;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleReader implements Reader {
    private final BufferedReader reader;

    public ConsoleReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}