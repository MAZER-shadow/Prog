package ru.ifmo.se.common.exception;

/**
 * Исключение, которое выбрасывается при возникновении ошибок ввода-вывода (I/O).
 * Наследуется от {@link RuntimeException}, что делает его необязательным для обработки.
 */
public class IORuntimeException extends RuntimeException {
    public IORuntimeException(String message) {
        super(message);
    }
}
