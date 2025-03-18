package ifmo.se.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
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
public class LabWorkDto implements Comparable<LabWorkDto>, Serializable {
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private double minimalPoint;
    private Float maximumPoint;
    private Difficulty difficulty;
    private Person author;

    public LabWorkDto() {
    }

    /**
     * Возвращает строковое представление объекта LabWork.
     *
     * @return Строка, содержащая информацию о лабораторной работе: идентификатор, название, координаты,
     * дата создания, минимальная и максимальная оценка, сложность и информация об авторе.
     */
    public String aboutLabWorkDto() {
        return "LabWork{" +
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
    public int compareTo(LabWorkDto o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LabWorkDto that = (LabWorkDto) o;
        return Double.compare(minimalPoint, that.minimalPoint) == 0
                && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates)
                && Objects.equals(creationDate, that.creationDate) && Objects.equals(maximumPoint, that.maximumPoint)
                && difficulty == that.difficulty && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate, minimalPoint, maximumPoint, difficulty, author);
    }
}
