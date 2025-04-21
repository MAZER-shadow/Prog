package ru.ifmo.se.server.exception;

/**
 * Исключение FileReadException выбрасывается при возникновении ошибок, связанных с чтением из файла.
 * Наследуется от RuntimeException, что делает его необязательным для обработки в коде.
 */
public class FileReadException extends RuntimeException {
    /**
     * Создает новое исключение FileReadException с указанным сообщением об ошибке.
     *
     * @param message Сообщение об ошибке, которое будет передано в родительский класс RuntimeException.
     */
    public FileReadException(String message) {
        super(message);
    }
}
