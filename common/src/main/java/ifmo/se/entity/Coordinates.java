package ifmo.se.entity;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 • Класс Coordinates представляет координаты точки в двумерном пространстве.
 • Координаты представлены целым числом X и длинным целым числом Y.
 */
@Getter
@Builder
public class Coordinates implements Serializable {
    /**
     * Координата по оси X.
     */
    private int x;

    /**
     * Координата по оси Y.
     */
    private long y;
}
