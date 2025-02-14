package se.ifmo.io.impl;

import se.ifmo.exception.IORuntimeException;
import se.ifmo.io.Writer;
import java.io.BufferedWriter;
import java.io.IOException;

public class WriterImpl implements Writer {
    private BufferedWriter out;

    public WriterImpl(BufferedWriter out) {
        this.out = out;
    }

    public void print(String s) {
        try {
            out.write(s);
            out.flush();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    @Override
    public void println(String s) {
        try {
            out.write(s + "\n");
            out.flush();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }
}
