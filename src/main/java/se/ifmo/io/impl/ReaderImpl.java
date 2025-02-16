package se.ifmo.io.impl;

import se.ifmo.exception.IORuntimeException;
import se.ifmo.exception.NonNullException;
import se.ifmo.io.Reader;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Класс ReaderImpl реализует интерфейс Reader для чтения текстовых данных с использованием BufferedReader.
 * Он предоставляет метод для чтения строки текста и обработки возможных ошибок.
 */
public class ReaderImpl implements Reader {
    private final BufferedReader reader;

    /**
     * Конструктор класса ReaderImpl. Инициализирует объект с указанным BufferedReader.
     *
     * @param reader BufferedReader, используемый для чтения текстовых данных.
     */
    public ReaderImpl(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Читает строку текста. Если строка равна null, выбрасывает исключение NonNullException.
     *
     * @return Прочитанная строка.
     * @throws NonNullException Если прочитанная строка равна null.
     * @throws IORuntimeException Если возникает ошибка ввода-вывода при чтении строки.
     */
    @Override
    public String readLine() {
        try {
            String line = reader.readLine();
            if (line == null) {
                throw new NonNullException("ввод null делается через ввод пустой строки((");
            }
            return line;
        } catch (IOException e) {
            throw new IORuntimeException();
        }
    }
}
