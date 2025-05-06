package ru.ifmo.se.server.dao;

import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;

import java.util.List;
import java.util.Optional;

public interface LabWorkDao {

    /**
     * Добавляет новый объект типа T в хранилище данных.
     *
     * @param entity Объект для добавления.
     * @return Добавленный объект.
     */
    LabWork add(LabWork entity);

    /**
     * Обновляет объект типа T в хранилище данных по-указанному ID.
     *
     * @param id     ID объекта для обновления.
     * @param entity Объект с новыми данными.
     */
    void updateById(long id, LabWork entity, User user);

    /**
     * Удаляет объект из хранилища данных по указанному ID.
     *
     * @param id ID объекта для удаления.
     * @return true, если объект был успешно удален, false в противном случае.
     */
    boolean removeById(long id, User user);


    /**
     * Возвращает объект типа T из хранилища данных по указанному ID.
     *
     * @param id ID объекта для поиска.
     * @return Optional, содержащий найденный объект, или пустой Optional, если объект не найден.
     */
    Optional<LabWork> getById(long id);
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
     * Проверяет, существует ли объект с указанным ID в хранилище данных.
     *
     * @param id ID объекта для проверки.
     * @return true, если объект существует, false в противном случае.
     */
    boolean existById(long id);
}
