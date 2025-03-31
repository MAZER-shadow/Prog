package ru.ifmo.se.server.command.special;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.ifmo.se.common.io.Writer;
import ru.ifmo.se.server.receiver.Receiver;

@AllArgsConstructor
public abstract class AbstractSpecialCommand {
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

    protected boolean checkParameters(String parameter) {
        return parameter.isEmpty();
    }
}
