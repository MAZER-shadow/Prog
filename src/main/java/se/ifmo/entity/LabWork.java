package se.ifmo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
