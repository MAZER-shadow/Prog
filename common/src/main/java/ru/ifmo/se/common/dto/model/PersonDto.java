package ru.ifmo.se.common.dto.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Builder
@ToString
/**
 * Класс Person представляет собой модель человека с именем, ростом и идентификатором паспорта.
 * Реализует интерфейс Comparable для сравнения объектов Person по имени.
 */
public class PersonDto implements Comparable<PersonDto>, Serializable {
    /**
     * Имя человека.
     */
    private String name;

    /**
     * Рост человека в сантиметрах.
     */
    private Integer height;

    /**
     * Уникальный идентификатор паспорта человека.
     */
    private String passportID;

    /**
     * Сравнивает текущий объект Person с другим объектом Person по имени.
     *
     * @param o Объект Person, с которым происходит сравнение.
     * @return Отрицательное число, ноль или положительное число,
     * если текущий объект меньше, равен или больше объекта o соответственно.
     */
    @Override
    public int compareTo(PersonDto o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDto person = (PersonDto) o;
        return Objects.equals(name, person.name) && Objects.equals(height, person.height)
                && Objects.equals(passportID, person.passportID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, passportID);
    }

    /**
     * Возвращает строковое представление объекта Person.
     *
     * @return Строка, содержащая информацию о человеке: имя, рост и идентификатор паспорта.
     */
    public String aboutPerson() {
        return "Person{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", passportID='" + passportID + '\'' +
                '}';
    }
}
