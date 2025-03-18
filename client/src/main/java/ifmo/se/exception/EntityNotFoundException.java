package ifmo.se.exception;

/**
 * Исключение EntityNotFoundException выбрасывается при попытке доступа к сущности, которая не найдена в базе данных.
 * Наследуется от RuntimeException, что делает его необязательным для обработки в коде.
 */
public class EntityNotFoundException extends RuntimeException {
    /**
     * Создает новое исключение EntityNotFoundException с указанным сообщением об ошибке.
     *
     * @param message Сообщение об ошибке, которое будет передано в родительский класс RuntimeException.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
