package se.ifmo.entity;

import lombok.Builder;

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

    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public String getPassportID() {
        return passportID;
    }
}