package ru.ifmo.se.common.exception;

/**
 * Исключение DumpDataBaseValidationException выбрасывается
 * при возникновении ошибок, связанных с валидацией дампа базы данных.
 * Наследуется от RuntimeException, что делает его необязательным для обработки в коде.
 */
public class DumpDataBaseValidationException extends RuntimeException {
    /**
     * Создает новое исключение DumpDataBaseValidationException с указанным сообщением об ошибке.
     *
     * @param message Сообщение об ошибке, которое будет передано в родительский класс RuntimeException.
     */
    public DumpDataBaseValidationException(String message) {
        super(message);
    }
}
