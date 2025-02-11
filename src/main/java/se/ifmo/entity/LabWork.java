package se.ifmo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Builder
@Getter
@ToString
public class LabWork {
    private long id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private double minimalPoint;
    private Float maximumPoint;
    private Difficulty difficulty;
    private Person author;

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
                ", автор работы=" + author.getName() +
                '}';
    }
}