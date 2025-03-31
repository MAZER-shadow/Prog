package ru.ifmo.se.server.io.impl;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.ifmo.se.server.exception.IORuntimeException;
import ru.ifmo.se.server.io.JsonWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Класс JsonWriterImpl реализует интерфейс JsonWriter для записи объектов в формате JSON в файл.
 * Он использует библиотеку Gson для сериализации объектов и записи их в указанный файл.
 *
 * @param <T> Тип объекта, который будет записан в формате JSON.
 */
public class JsonWriterImpl<T> implements JsonWriter<T> {
    /**
     * Записывает объект в формате JSON в файл по указанному пути.
     * Использует библиотеку Gson для сериализации объекта и форматирования JSON.
     *
     * @param object Объект, который необходимо записать в формате JSON.
     * @param path   Путь к файлу, в который будет записан объект.
     * @throws IORuntimeException Если возникает ошибка ввода-вывода при записи в файл.
     */
    @Override
    public void writeToJson(T object, String path) {
        Gson gson = Converters.registerLocalDate(new GsonBuilder())
                .setPrettyPrinting()
                .create();

        try (FileOutputStream fileOutputStream = new FileOutputStream(path);
                OutputStreamWriter streamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                PrintWriter printWriter = new PrintWriter(streamWriter);
                BufferedWriter writer = new BufferedWriter(printWriter)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            throw new IORuntimeException(e.getMessage());
        }
    }
}
