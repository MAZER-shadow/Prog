package ru.ifmo.se.common.exception;

/**
 * Исключение NonNullScriptException выбрасывается при
 * возникновении ошибок, связанных с вводом null при выполнении скрипта.
 * Наследуется от RuntimeException, что делает его необязательным для обработки в коде.
 */
public class NonNullScriptException extends RuntimeException {
    /**
     * Создает новое исключение NonNullScriptException с указанным сообщением об ошибке.
     *
     * @param message Сообщение об ошибке, которое будет передано в родительский класс RuntimeException.
     */
    public NonNullScriptException(String message) {
        super(message);
    }
}
