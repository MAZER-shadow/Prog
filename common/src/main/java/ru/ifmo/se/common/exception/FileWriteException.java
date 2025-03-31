package ru.ifmo.se.common.exception;

/**
 * Исключение FileWriteException выбрасывается при возникновении ошибок, связанных с записью в файл.
 * Наследуется от RuntimeException, что делает его необязательным для обработки в коде.
 */
public class FileWriteException extends RuntimeException {
    /**
     * Создает новое исключение FileWriteException с указанным сообщением об ошибке.
     *
     * @param message Сообщение об ошибке, которое будет передано в родительский класс RuntimeException.
     */
    public FileWriteException(String message) {
        super(message);
    }
}
