package ru.ifmo.se.common.exception;

/**
 * Исключение CommandNotFoundException выбрасывается при попытке выполнить команду, которая не найдена.
 * Наследуется от RuntimeException, что делает его необязательным для обработки в коде.
 */
public class CommandNotFoundException extends RuntimeException {
    /**
     * Создает новое исключение CommandNotFoundException с указанным сообщением об ошибке.
     *
     * @param message Сообщение об ошибке, которое будет передано в родительский класс RuntimeException.
     */
    public CommandNotFoundException(String message) {
        super(message);
    }
}
