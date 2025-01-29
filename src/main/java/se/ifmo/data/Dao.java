package se.ifmo.data;

import se.ifmo.entity.Person;
import java.util.List;

public interface Dao {
    List<Person> getAll();

    Person create(String name);
    /*считывать мы в теории должны не только список персонов(например команды),
    но как сделать иначе тоже пока не знаю может разделить методы на readPerson
    и readCommand*/

    List<Person> read(String path);

    void delete(Person person);

    void add(Person person);

    void write();
}