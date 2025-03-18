package ifmo.se.exception;

/**
 * Исключение NonNullException выбрасывается при возникновении ошибок, связанных с некорректными данными,
 * которые не должны быть null. Наследуется от RuntimeException, что делает его необязательным для обработки в коде.
 */
public class NonNullException extends RuntimeException {
    /**
     * Создает новое исключение NonNullException с указанным сообщением об ошибке.
     *
     * @param message Сообщение об ошибке, которое будет передано в родительский класс RuntimeException.
     */
    public NonNullException(String message) {
        super(message);
    }
}
