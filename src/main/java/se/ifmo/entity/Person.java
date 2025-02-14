package se.ifmo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Person implements Comparable<Person> {
    private String name;
    private Integer height;
    private String passportID;

    @Override
    public int compareTo(Person o) {
        return name.compareTo(o.name);
    }

    public String aboutPerson() {
        return "Person{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", passportID='" + passportID + '\'' +
                '}';
    }
}
