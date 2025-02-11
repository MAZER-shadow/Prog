package se.ifmo.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Coordinates {
    private int x; //Значение поля должно быть больше -437
    private long y;
}
