package ru.ifmo.se.server.entity;

import org.junit.jupiter.api.Test;
import ru.ifmo.se.server.AbstractTest;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest extends AbstractTest {

    @Test
    void testCompareTo() {
        Person person1 = Person.builder()
                .name("Alice")
                .build();

        Person person2 = Person.builder()
                .name("Bob")
                .build();

        assertTrue(person1.compareTo(person2) < 0);
        assertTrue(person2.compareTo(person1) > 0);
        assertEquals(0, person1.compareTo(person1));
    }

    @Test
    void testAboutPerson() {
        Person person = Person.builder()
                .name("John")
                .height(180)
                .passportID("AB123456")
                .build();

        String expected = "Person{name='John', height=180, passportID='AB123456'}";
        assertEquals(expected, person.aboutPerson());
    }

    @Test
    void testEqualsAndHashCode() {
        Person person1 = Person.builder()
                .name("John")
                .height(180)
                .passportID("AB123456")
                .build();

        Person person2 = Person.builder()
                .name("John")
                .height(180)
                .passportID("AB123456")
                .build();

        Person person3 = Person.builder()
                .name("Different")
                .height(170)
                .passportID("CD654321")
                .build();

        assertEquals(person1, person2);
        assertNotEquals(person1, person3);
        assertEquals(person1.hashCode(), person2.hashCode());
        assertNotEquals(person1.hashCode(), person3.hashCode());
    }
}