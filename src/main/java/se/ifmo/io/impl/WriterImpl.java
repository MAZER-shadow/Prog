package se.ifmo.io.impl;

import se.ifmo.exception.IORuntimeException;
import se.ifmo.io.Writer;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Класс WriterImpl реализует интерфейс Writer для вывода текстовых данных с использованием BufferedWriter.
 * Он предоставляет методы для печати строк с переходом на новую строку и без него.
 */
public class WriterImpl implements Writer {
    private BufferedWriter out;

    /**
     * Конструктор класса WriterImpl. Инициализирует объект с указанным BufferedWriter.
     *
     * @param out BufferedWriter, используемый для вывода текстовых данных.
     */
    public WriterImpl(BufferedWriter out) {
        this.out = out;
    }

    /**
     * Выводит строку без перехода на новую строку.
     *
     * @param s Строка, которую необходимо вывести.
     * @throws IORuntimeException Если возникает ошибка ввода-вывода при записи строки.
     */
    @Override
    public void print(String s) {
        try {
            out.write(s);
            out.flush();
        } catch (IOException e) {
            throw new IORuntimeException();
        }
    }

    /**
     * Выводит строку с переходом на новую строку.
     *
     * @param s Строка, которую необходимо вывести.
     * @throws IORuntimeException Если возникает ошибка ввода-вывода при записи строки.
     */
    @Override
    public void println(String s) {
        try {
            out.write(s + "\n");
            out.flush();
        } catch (IOException e) {
            throw new IORuntimeException();
        }
    }
}
