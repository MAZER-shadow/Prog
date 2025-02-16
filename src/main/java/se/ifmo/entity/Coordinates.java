package se.ifmo.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
/**
 • Класс Coordinates представляет координаты точки в двумерном пространстве.
 • Координаты представлены целым числом X и длинным целым числом Y.
 */
public class Coordinates {
    /**
     * Координата по оси X.
     */
    private int x;

    /**
     * Координата по оси Y.
     */
    private long y;
}
