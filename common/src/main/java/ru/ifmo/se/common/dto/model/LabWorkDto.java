package ru.ifmo.se.common.dto.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Builder
@Getter
public class LabWorkDto implements Serializable {
    private long id;
    private String name;
    private CoordinatesDto coordinates;
    private java.time.LocalDate creationDate;
    private double minimalPoint;
    private Float maximumPoint;
    private DifficultyDto difficulty;
    private PersonDto author;

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
}
