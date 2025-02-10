package se.ifmo.data;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();

    void clear();

    boolean delete(T person);

    void add(T person);
}