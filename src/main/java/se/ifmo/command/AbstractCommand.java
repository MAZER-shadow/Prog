package se.ifmo.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import se.ifmo.io.Writer;
import se.ifmo.receiver.Receiver;
@RequiredArgsConstructor
/**
 * Абстрактный класс, представляющий команду для взаимодействия с {@link Receiver}.
 * Каждая команда должна реализовывать метод {@link #execute(String)} для выполнения своей логики.
 * Хранит информацию о названии команды, её описании и объекте для вывода сообщений.
 */
public abstract class AbstractCommand {

    /**
     * Объект, управляющий взаимодействием с базой данных или хранилищем данных.
     */
    @NonNull
    protected Receiver receiver;

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
    public abstract void execute(String parameters);
}
