package ru.ifmo.se.server.controller.command;


import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.service.LabWorkService;


@AllArgsConstructor
public abstract class AbstractCommand {

    /**
     * Объект, управляющий взаимодействием с базой данных или хранилищем данных.
     */
    protected LabWorkService labWorkService;

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
     * Абстрактный метод для выполнения команды.
     * Каждая команда должна реализовать свою логику выполнения в этом методе.
     */
    public abstract Response execute(Request request);
}
