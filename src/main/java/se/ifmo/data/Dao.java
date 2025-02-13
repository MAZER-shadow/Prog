package se.ifmo.data;

import se.ifmo.entity.LabWork;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();

    void clear();

    boolean delete(T labWork);

    void add(T labWork);

    DatabaseMetaData getDatabaseMetaData();

    void updateById(int id, LabWork labWork);
}