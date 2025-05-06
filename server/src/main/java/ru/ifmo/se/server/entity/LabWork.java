package ru.ifmo.se.server.entity;

import lombok.*;

import java.util.Objects;

@Setter
@Builder
@Getter
@ToString
/**
 * Класс LabWork представляет собой модель лабораторной работы с уникальным идентификатором, названием, координатами,
 * датой создания, минимальной и максимальной оценкой, сложностью и автором.
 * Реализует интерфейс Comparable для сравнения объектов LabWork по названию.
 */
public class LabWork implements Comparable<LabWork> {
    private long id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private double minimalPoint;
    private Float maximumPoint;
    private Difficulty difficulty;
    private Person author;
    private User user;

    /**
     * Возвращает строковое представление объекта LabWork.
     *
     * @return Строка, содержащая информацию о лабораторной работе: идентификатор, название, координаты,
     * дата создания, минимальная и максимальная оценка, сложность и информация об авторе.
     */
    public String aboutLabWork() {
        return "LabWork{" +
                "id работы=" + id +
                ", имя работы='" + name + '\'' +
                ", координата X работы=" + coordinates.getX() +
                ", координата Y работы=" + coordinates.getY() +
                ", дата создания=" + creationDate +
                ", минимальная оценка=" + minimalPoint +
                ", максимальная оценка=" + maximumPoint +
                ", сложность=" + difficulty +
                ", автор работы=" + author.aboutPerson() +
                '}';
    }

    /**
     * Сравнивает текущий объект LabWork с другим объектом LabWork по названию.
     *
     * @param o Объект LabWork, с которым происходит сравнение.
     * @return Отрицательное число, ноль или положительное число,
     * если текущий объект меньше, равен или больше объекта o соответственно.
     */
    @Override
    public int compareTo(LabWork o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LabWork labWork = (LabWork) o;
        return id == labWork.id && Double.compare(minimalPoint, labWork.minimalPoint) == 0 &&
                Objects.equals(name, labWork.name) && Objects.equals(coordinates, labWork.coordinates)
                && Objects.equals(creationDate, labWork.creationDate)
                && Objects.equals(maximumPoint, labWork.maximumPoint)
                && difficulty == labWork.difficulty && Objects.equals(author, labWork.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, minimalPoint,
                maximumPoint, difficulty, author);
    }
}
