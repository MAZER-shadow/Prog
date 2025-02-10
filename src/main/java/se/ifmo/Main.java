package se.ifmo;

import se.ifmo.preset.Starter;
import se.ifmo.preset.impl.StarterImpl;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Starter starter = new StarterImpl();
        starter.run();
    }
}