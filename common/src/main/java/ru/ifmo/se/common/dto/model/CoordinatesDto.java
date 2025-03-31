package ru.ifmo.se.common.dto.model;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CoordinatesDto implements Serializable {
    /**
     * Координата по оси X.
     */
    private int x;

    /**
     * Координата по оси Y.
     */
    private long y;
}
