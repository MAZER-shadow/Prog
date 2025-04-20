package ru.ifmo.se.server.database;

import java.util.Optional;

/**
 * Интерфейс Data Access Object (DAO) для доступа к данным объектов типа T.
 * Предоставляет методы для основных операций CRUD (Create, Read, Update, Delete)
 * и получения метаданных базы данных.
 *
 * @param <T> Тип объектов, с которыми работает данный DAO.
 */
public interface Dao<T> {

    /**
     * Добавляет новый объект типа T в хранилище данных.
     *
     * @param entity Объект для добавления.
     * @return Добавленный объект.
     */
    T add(T entity);

    /**
     * Обновляет объект типа T в хранилище данных по-указанному ID.
     *
     * @param id      ID объекта для обновления.
     * @param entity Объект с новыми данными.
     */
    void updateById(long id, T entity);

    /**
     * Удаляет объект из хранилища данных по указанному ID.
     *
     * @param id ID объекта для удаления.
     * @return true, если объект был успешно удален, false в противном случае.
     */
    boolean removeById(long id);


    /**
     * Возвращает объект типа T из хранилища данных по указанному ID.
     *
     * @param id ID объекта для поиска.
     * @return Optional, содержащий найденный объект, или пустой Optional, если объект не найден.
     */
    Optional<T> getById(long id);
}
