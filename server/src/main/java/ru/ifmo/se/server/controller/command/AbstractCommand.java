package ru.ifmo.se.server.controller.command;

import lombok.Getter;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

public abstract class AbstractCommand {
    @Getter
    private final Condition condition;
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

    public AbstractCommand(String name, String description, Condition condition) {
        this.name = name;
        this.description = description;
        this.condition = condition;
    }

    public AbstractCommand(LabWorkService labWorkService, String name, String description, Condition condition) {
        this.name = name;
        this.labWorkService = labWorkService;
        this.description = description;
        this.condition = condition;
    }

    /**
     * Абстрактный метод для выполнения команды.
     * Каждая команда должна реализовать свою логику выполнения в этом методе.
     */


    public abstract Response execute(Request request, User user);

}
