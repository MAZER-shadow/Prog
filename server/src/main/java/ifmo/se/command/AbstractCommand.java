package ifmo.se.command;

import ifmo.se.io.Writer;
import ifmo.se.receiver.Receiver;
import ifmo.se.request.AbstractRequest;
import ifmo.se.response.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Абстрактный класс, представляющий команду для взаимодействия с {@link Receiver}.
 * Хранит информацию о названии команды, её описании и объекте для вывода сообщений.
 */
@AllArgsConstructor
public abstract class AbstractCommand {

    protected Receiver receiver;
    /**
     * Объект, управляющий взаимодействием с базой данных или хранилищем данных.
     */

    /**
     * Название команды.
     */
    @Getter
    protected final String name;

    /**
     * Описание команды.
     */
    @Getter
    protected final String description;

    /**
     * Писатель для вывода сообщений в консоль или файл.
     */
    protected final Writer writer;

    /**
     * Абстрактный метод для выполнения команды.
     * Каждая команда должна реализовать свою логику выполнения в этом методе.
     *
     * @param parameters Параметры, переданные в команду.
     */
    public abstract AbstractResponse execute(String parameters, AbstractRequest request);
}
