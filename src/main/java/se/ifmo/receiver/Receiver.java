package se.ifmo.receiver;

import se.ifmo.database.data.Dao;
import se.ifmo.entity.LabWork;
import se.ifmo.database.dump.DatabaseDump;

import java.util.List;

/**
 * Класс Receiver представляет собой промежуточный слой между командами и базой данных.
 * Он предоставляет методы для выполнения операций с базой данных,
 * таких как добавление, удаление и обновление объектов LabWork.
 */
public class Receiver {
    private final Dao<LabWork> db;

    /**
     * Конструктор класса Receiver. Инициализирует объект с указанной базой данных.
     *
     * @param db Объект Dao, представляющий базу данных для работы с объектами LabWork.
     */
    public Receiver(Dao<LabWork> db) {
        this.db = db;
    }

    /**
     * Добавляет новый объект LabWork в базу данных.
     *
     * @param person Объект LabWork, который необходимо добавить.
     * @return Добавленный объект LabWork.
     */
    public LabWork add(LabWork person) {
        return db.add(person);
    }

    /**
     * Возвращает максимальный идентификатор среди всех объектов LabWork в базе данных.
     *
     * @return Максимальный идентификатор.
     */
    public long getMaxId() {
        long id = 0;
        for (LabWork labWork : getAll()) {
            if (labWork.getId() > id) {
                id = labWork.getId();
            }
        }
        return id;
    }

    /**
     * Очищает базу данных, удаляя все объекты LabWork.
     */
    public void clear() {
        db.clear();
    }

    /**
     * Возвращает список всех объектов LabWork в базе данных.
     *
     * @return Список объектов LabWork.
     */
    public List<LabWork> getAll() {
        return db.getAll();
    }

    /**
     * Возвращает количество объектов LabWork в базе данных.
     *
     * @return Количество объектов LabWork.
     */
    public long getSize() {
        return db.getAll().size();
    }

    /**
     * Возвращает дамп базы данных, содержащий метаданные и список всех объектов LabWork.
     *
     * @return Объект DatabaseDump.
     */
    public DatabaseDump getDatabaseDump() {
        return new DatabaseDump(db.getDatabaseMetaData(), getAll());
    }

    /**
     * Проверяет наличие объекта LabWork с указанным идентификатором в базе данных.
     *
     * @param id Идентификатор объекта LabWork.
     * @return true, если объект с указанным идентификатором существует, иначе false.
     */
    public boolean existById(long id) {
        return db.existById(id);
    }

    /**
     * Обновляет объект LabWork с указанным идентификатором.
     *
     * @param id      Идентификатор объекта LabWork, который необходимо обновить.
     * @param labWork Новый объект LabWork для замены старого.
     */
    public void updateById(long id, LabWork labWork) {
        db.updateById(id, labWork);
    }

    /**
     * Удаляет объект LabWork с указанным идентификатором из базы данных.
     *
     * @param id Идентификатор объекта LabWork, который необходимо удалить.
     * @return true, если объект был успешно удален, иначе false.
     */
    public boolean removeById(long id) {
        return db.removeById(id);
    }
}
