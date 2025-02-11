package se.ifmo.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Person {
    private String name;
    private Integer height;
    private String passportID;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}