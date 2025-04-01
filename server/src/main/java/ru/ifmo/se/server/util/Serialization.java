package ru.ifmo.se.server.util;

import ru.ifmo.se.common.exception.IORuntimeException;

import java.io.*;
import java.nio.ByteBuffer;

public class Serialization {
    public static byte [] serialize(Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new IORuntimeException(e.getMessage());
        }
    }

    public static Object deserialize(ByteBuffer buffer) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IORuntimeException(e.getMessage());
        }
    }
}
