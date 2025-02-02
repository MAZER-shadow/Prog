package se.ifmo.entity;

public class Person {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private long height;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;
    private Location location;

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