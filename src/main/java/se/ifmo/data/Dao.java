package se.ifmo.data;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();

    /*считывать мы в теории должны не только список персонов(например команды),
    но как сделать иначе тоже пока не знаю может разделить методы на readPerson
    и readCommand*/

    void clear();

    void delete(T person);

    void add(T person);

    void write();
}