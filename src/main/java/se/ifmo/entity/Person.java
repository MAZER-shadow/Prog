package se.ifmo.entity;

public class Person {
    private String name;
    private Integer height;
    private String passportID;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}