package ru.ifmo.se.client.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Writer;

/**
 * Каждая команда должна реализовывать метод {@link #execute(String)} для выполнения своей логики.
 * Хранит информацию о названии команды, её описании и объекте для вывода сообщений.
 */
@AllArgsConstructor
public abstract class AbstractCommand {

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
    public abstract Request makeRequest(String parameters);

    public abstract void handleResponse(Response response);
}
