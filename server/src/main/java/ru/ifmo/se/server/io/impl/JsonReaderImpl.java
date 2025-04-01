package ru.ifmo.se.server.io.impl;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.ifmo.se.common.exception.IORuntimeException;
import ru.ifmo.se.server.io.JsonReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Класс JsonReaderImpl реализует интерфейс JsonReader
 * для чтения объектов из файла в формате JSON.
 * Использует библиотеку Gson для десериализации JSON-данных в объекты указанного типа.
 *
 * @param <T> Тип объекта, который будет прочитан из JSON.
 */
public class JsonReaderImpl<T> implements JsonReader<T> {
    private final Class<T> clazz;

    /**
     * Конструктор класса JsonReaderImpl. Инициализирует объект с указанным классом типа.
     *
     * @param clazz Класс типа, который будет использоваться для десериализации JSON.
     */
    public JsonReaderImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Читает объект из файла в формате JSON по указанному пути.
     * Использует библиотеку Gson для десериализации JSON-данных.
     *
     * @param path Путь к файлу, из которого будет прочитан объект.
     * @return Объект, прочитанный из файла в формате JSON.
     * @throws IllegalStateException Если возникает ошибка при чтении файла или десериализации JSON.
     * @throws IORuntimeException Если возникает ошибка ввода-вывода при чтении файла.
     */
    @Override
    public T readJson(String path) throws IllegalStateException {
        try (FileReader fileReader = new FileReader(path, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(fileReader)) {
            Gson gson = Converters.registerLocalDate(new GsonBuilder())
                    .setPrettyPrinting()
                    .create();
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            throw new IORuntimeException(e.getMessage());
        }
    }
}
