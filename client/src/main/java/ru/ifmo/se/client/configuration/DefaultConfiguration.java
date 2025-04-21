package ru.ifmo.se.client.configuration;

import java.nio.file.Paths;

/**
 * Класс DefaultConfiguration содержит конфигурационные данные по умолчанию для приложения.
 * В частности, он предоставляет путь по умолчанию для сохранения файла.
 */
public class DefaultConfiguration {
    /**
     * Путь по умолчанию для сохранения файла.
     * Путь задается как абсолютный путь к файлу "FileWithLabWork" в текущей директории.
     */
    public final static String DEFAULT_PATH = Paths.get("FileWithLabWork").toAbsolutePath().toString();
}
