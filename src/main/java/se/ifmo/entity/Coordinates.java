package se.ifmo.entity;

import lombok.Builder;

@Builder
public class Coordinates {
    private int x; //Значение поля должно быть больше -437
    private long y;

    public int getX() {
        return x;
    }

    public long getY() {
        return y;
    }
}
