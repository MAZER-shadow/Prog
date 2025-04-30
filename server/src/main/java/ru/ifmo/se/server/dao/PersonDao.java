package ru.ifmo.se.server.dao;

import ru.ifmo.se.server.entity.Person;

public interface PersonDao {
    Person add(Person person);

    /**
     * Обновляет объект типа T в хранилище данных по-указанному ID.
     *
     * @param id ID объекта для обновления.
     */
    void updateById(long id, Person person);

    /**
     * Удаляет объект из хранилища данных по указанному ID.
     *
     * @param id ID объекта для удаления.
     * @return true, если объект был успешно удален, false в противном случае.
     */
    boolean removeById(long id);
}
