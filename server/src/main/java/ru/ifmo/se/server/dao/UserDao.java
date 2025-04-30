package ru.ifmo.se.server.dao;

import ru.ifmo.se.server.entity.User;

import java.util.Optional;

public interface UserDao {

    /**
     * Добавляет новый объект типа T в хранилище данных.
     *
     * @param entity Объект для добавления.
     * @return Добавленный объект.
     */
    User add(User entity);

    /**
     * Обновляет объект типа T в хранилище данных по-указанному ID.
     *
     * @param id     ID объекта для обновления.
     * @param entity Объект с новыми данными.
     */
    void updateById(long id, User entity);

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
    Optional<User> getById(long id);
    Optional<User> findByUsername(String username);
}
