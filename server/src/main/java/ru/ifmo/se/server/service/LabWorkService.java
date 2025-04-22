package ru.ifmo.se.server.service;

import ru.ifmo.se.annotationproccesor.Transactional;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;

import java.util.List;

/**
 * Интерфейс для работы с базой данных LabWork
 */
public interface LabWorkService {
    /**
     * Добавляет новый объект LabWork в базу данных.
     *
     * @param labWork Объект LabWork, который необходимо добавить.
     * @return Добавленный объект LabWork.
     */
    @Transactional
    LabWork add(LabWork labWork);

    /**
     * Возвращает максимальный идентификатор среди всех объектов LabWork в базе данных.
     *
     * @return Максимальный идентификатор.
     */
    @Transactional
    long getMaxId();

    /**
     * Очищает базу данных, удаляя все объекты LabWork.
     */
    @Transactional
    void clear(User user);

    /**
     * Возвращает список всех объектов LabWork в базе данных.
     *
     * @return Список объектов LabWork.
     */
    @Transactional
    List<LabWork> getAll();

    /**
     * Возвращает количество объектов LabWork в базе данных.
     *
     * @return Количество объектов LabWork.
     */
    @Transactional
    long getSize();


    /**
     * Проверяет наличие объекта LabWork с указанным идентификатором в базе данных.
     *
     * @param id Идентификатор объекта LabWork.
     * @return true, если объект с указанным идентификатором существует, иначе false.
     */
    @Transactional
    boolean existById(long id);

    /**
     * Обновляет объект LabWork с указанным идентификатором.
     *
     * @param id      Идентификатор объекта LabWork, который необходимо обновить.
     * @param labWork Новый объект LabWork для замены старого.
     */
    @Transactional
    void updateById(long id, LabWork labWork, User user);

    /**
     * Удаляет объект LabWork с указанным идентификатором из базы данных.
     *
     * @param id Идентификатор объекта LabWork, который необходимо удалить.
     * @return true, если объект был успешно удален, иначе false.
     */
    @Transactional
    boolean removeById(long id, User user);
}