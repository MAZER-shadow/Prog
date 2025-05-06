package ru.ifmo.se.server.entity;

import org.junit.jupiter.api.Test;
import ru.ifmo.se.server.AbstractTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LabWorkTest extends AbstractTest {

    @Test
    void testCompareTo() {
        LabWork lab1 = LabWork.builder()
                .name("A")
                .build();

        LabWork lab2 = LabWork.builder()
                .name("B")
                .build();

        assertTrue(lab1.compareTo(lab2) < 0);
        assertTrue(lab2.compareTo(lab1) > 0);
        assertEquals(0, lab1.compareTo(lab1));
    }

    @Test
    void testAboutLabWork() {
        Person author = Person.builder()
                .name("John")
                .height(180)
                .passportID("AB123456")
                .build();

        Coordinates coordinates = new Coordinates(); // Предполагается, что класс Coordinates существует
        coordinates.setX(10);
        coordinates.setY(20);

        LabWork lab = LabWork.builder()
                .id(1)
                .name("Test Lab")
                .coordinates(coordinates)
                .creationDate(LocalDate.of(2023, 1, 1))
                .minimalPoint(5.0)
                .maximumPoint(10.0f)
                .difficulty(Difficulty.NORMAL) // Предполагается, что enum Difficulty существует
                .author(author)
                .build();

        String expected = "LabWork{id работы=1, имя работы='Test Lab', координата X работы=10, координата Y работы=20, " +
                "дата создания=2023-01-01, минимальная оценка=5.0, максимальная оценка=10.0, " +
                "сложность=NORMAL, автор работы=Person{name='John', height=180, passportID='AB123456'}}";

        assertEquals(expected, lab.aboutLabWork());
    }

    @Test
    void testEqualsAndHashCode() {
        LabWork lab1 = LabWork.builder()
                .id(1)
                .name("Test")
                .minimalPoint(5.0)
                .build();

        LabWork lab2 = LabWork.builder()
                .id(1)
                .name("Test")
                .minimalPoint(5.0)
                .build();

        LabWork lab3 = LabWork.builder()
                .id(2)
                .name("Different")
                .minimalPoint(10.0)
                .build();

        assertEquals(lab1, lab2);
        assertNotEquals(lab1, lab3);
        assertEquals(lab1.hashCode(), lab2.hashCode());
        assertNotEquals(lab1.hashCode(), lab3.hashCode());
    }
}