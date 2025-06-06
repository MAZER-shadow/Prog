package ru.ifmo.se.server.dao;

import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;

import java.sql.DatabaseMetaData;
import java.util.List;

public interface LabWorkDao extends Dao<LabWork> {

    /**
     * Возвращает список всех объектов типа T.
     *
     * @return Список всех объектов.
     */
    List<LabWork> getAll();

    /**
     * Удаляет все объекты из хранилища данных.
     */
    void clear(User user);

    /**
     * Возвращает метаданные базы данных.
     *
     * @return Объект DatabaseMetaData, содержащий метаданные.
     */
    DatabaseMetaData getDatabaseMetaData();


    /**
     * Проверяет, существует ли объект с указанным ID в хранилище данных.
     *
     * @param id ID объекта для проверки.
     * @return true, если объект существует, false в противном случае.
     */
    boolean existById(long id);
}
