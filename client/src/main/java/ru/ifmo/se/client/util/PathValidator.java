package ru.ifmo.se.client.util;

import ru.ifmo.se.common.io.Writer;

import java.io.File;

import static ru.ifmo.se.client.configuration.DefaultConfiguration.DEFAULT_PATH;

public class PathValidator {
    private final Writer writer;

    public PathValidator(Writer writer) {
        this.writer = writer;
    }

    public boolean isValidDefaultPathToSave() {
        File pFile = new File(DEFAULT_PATH);
        File pDir = new File(pFile.getParent().toString());
        if (!isValidDirectoryPathToSave(pDir)) {
            return false;
        }
        if (pFile.isFile()) {
            if (!pFile.canWrite()) {
                writer.println(String.format("Ошибка доступа записи в -> %s", DEFAULT_PATH));
                return false;
            }
            return true;
        }
        return true;
    }

    public boolean isValidPathToSave(String path) {
        if (path == null) {
            return false;
        }
        File pFile = new File(path);
        if (pFile.getParentFile() == null) {
            return false;
        }
        File pDir = new File(pFile.getParent().toString());
        if (!isValidDirectoryPathToSave(pDir)) {
            return false;
        }
        if (!pFile.isFile()) {
            writer.println(String.format("Это не файл -> %s", pFile));
            return false;
        }
        if (!pFile.canWrite()) {
            writer.println(String.format("Ошибка доступа записи в -> %s", path));
            return false;
        }
        return true;
    }

    public boolean isValidPathToRead(String path) {
        File pFile = new File(path);
        if (pFile.getParent() == null) {
            writer.println(String.format("Это не файл -> %s", pFile));
            return false;
        }
        File pDir = new File(pFile.getParent().toString());
        if (!isValidDirectoryPathToRead(pDir)) {
            return false;
        }
        if (!pFile.isFile()) {
            writer.println(String.format("Это не файл -> %s", pFile));
            return false;
        }
        if (!pFile.canRead()) {
            writer.println(String.format("Ошибка доступа чтения у -> %s", path));
            return false;
        }
        return true;
    }

    private boolean isValidDirectoryPathToRead(File pDir) {
        if (!pDir.isDirectory()) {
            writer.println(String.format("Это не директория -> %s", pDir));
            return false;
        }
        if (!pDir.canRead()) {
            writer.println(String.format("Ошибка доступа чтения у -> %s", pDir));
            return false;
        }
        return true;
    }

    private boolean isValidDirectoryPathToSave(File pDir) {
        if (!pDir.isDirectory()) {
            writer.println(String.format("Это не директория -> %s", pDir));
            return false;
        }
        if (!pDir.canWrite()) {
            writer.println(String.format("Ошибка доступа записи в -> %s", pDir));
            return false;
        }
        return true;
    }
}
