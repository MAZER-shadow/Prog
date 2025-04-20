package ru.ifmo.se.server.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 • Класс Coordinates представляет координаты точки в двумерном пространстве.
 • Координаты представлены целым числом X и длинным целым числом Y.
 */
@Builder
@Setter
@Getter
public class Coordinates {
    private long id;
    /**
     * Координата по оси X.
     */
    private int x;

    /**
     * Координата по оси Y.
     */
    private long y;
    public Coordinates() {
    }

    public Coordinates(int x, long y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(long id, int x, long y) {
        this(x, y);
        this.id = id;
    }
}
