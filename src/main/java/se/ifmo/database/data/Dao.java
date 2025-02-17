package se.ifmo.database.data;

import se.ifmo.entity.LabWork;

import java.util.List;
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
     * Возвращает список всех объектов типа T.
     *
     * @return Список всех объектов.
     */
    List<T> getAll();

    /**
     * Удаляет все объекты из хранилища данных.
     */
    void clear();

    /**
     * Удаляет указанный объект типа T из хранилища данных.
     *
     * @param labWork Объект для удаления.
     * @return true, если объект был успешно удален, false в противном случае.
     */
    boolean delete(T labWork);

    /**
     * Добавляет новый объект типа T в хранилище данных.
     *
     * @param labWork Объект для добавления.
     * @return Добавленный объект.
     */
    LabWork add(T labWork);

    /**
     * Возвращает метаданные базы данных.
     *
     * @return Объект DatabaseMetaData, содержащий метаданные.
     */
    DatabaseMetaData getDatabaseMetaData();

    /**
     * Обновляет объект типа T в хранилище данных по указанному ID.
     *
     * @param id      ID объекта для обновления.
     * @param labWork Объект с новыми данными.
     */
    void updateById(long id, LabWork labWork);

    /**
     * Удаляет объект из хранилища данных по указанному ID.
     *
     * @param id ID объекта для удаления.
     * @return true, если объект был успешно удален, false в противном случае.
     */
    boolean removeById(long id);

    /**
     * Проверяет, существует ли объект с указанным ID в хранилище данных.
     *
     * @param id ID объекта для проверки.
     * @return true, если объект существует, false в противном случае.
     */
    boolean existById(long id);

    /**
     * Возвращает объект типа T из хранилища данных по указанному ID.
     *
     * @param id ID объекта для поиска.
     * @return Optional, содержащий найденный объект, или пустой Optional, если объект не найден.
     */
    Optional<LabWork> getById(long id);
}
