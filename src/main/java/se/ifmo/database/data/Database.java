package se.ifmo.database.data;

import se.ifmo.entity.LabWork;
import se.ifmo.database.dump.DatabaseDump;
import se.ifmo.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс Database реализует интерфейс Dao для работы с коллекцией объектов типа LabWork.
 * Предоставляет методы для управления коллекцией, такие как добавление, удаление, обновление и поиск элементов.
 * Также поддерживает метаданные базы данных, такие как размер коллекции.
 */
public class Database implements Dao<LabWork> {
    private final List<LabWork> listOfLabWork;
    private DatabaseMetaData databaseMetaData;

    /**
     * Конструктор класса Database. Инициализирует коллекцию объектов LabWork и метаданные базы данных
     * на основе предоставленного дампа базы данных.
     *
     * @param databaseDump Объект DatabaseDump, содержащий начальные данные для инициализации базы данных.
     *                     Если список LabWork в дампе равен null, создается пустая коллекция.
     */
    public Database(DatabaseDump databaseDump) {
        if (databaseDump.getListOfLabWork() == null) {
            listOfLabWork = new ArrayList<>();
        } else {
            listOfLabWork = databaseDump.getListOfLabWork();
        }
        this.databaseMetaData = databaseDump.getDatabaseMetaData();
    }

    /**
     * Возвращает список всех объектов LabWork в базе данных.
     *
     * @return Список объектов LabWork.
     */
    @Override
    public List<LabWork> getAll() {
        return listOfLabWork;
    }

    /**
     * Очищает коллекцию объектов LabWork.
     */
    @Override
    public void clear() {
        listOfLabWork.clear();
    }

    /**
     * Удаляет указанный объект LabWork из коллекции.
     *
     * @param labWork Объект LabWork, который необходимо удалить.
     * @return true, если объект был успешно удален, иначе false.
     */
    @Override
    public boolean delete(LabWork labWork) {
        return listOfLabWork.remove(labWork);
    }

    /**
     * Добавляет новый объект LabWork в коллекцию. Устанавливает уникальный идентификатор и дату создания.
     *
     * @param labwork Объект LabWork, который необходимо добавить.
     * @return Добавленный объект LabWork с установленными идентификатором и датой создания.
     */
    @Override
    public LabWork add(LabWork labwork) {
        long id = 0;
        for (LabWork labWork : listOfLabWork) {
            if (labWork.getId() > id) {
                id = labWork.getId();
            }
        }
        labwork.setId(++id);
        labwork.setCreationDate(LocalDate.now());
        listOfLabWork.add(labwork);
        return labwork;
    }

    /**
     * Обновляет объект LabWork с указанным идентификатором. Если объект с таким идентификатором не найден,
     * выбрасывает исключение EntityNotFoundException.
     *
     * @param id      Идентификатор объекта LabWork, который необходимо обновить.
     * @param labWork Новый объект LabWork для замены старого.
     * @throws EntityNotFoundException Если объект с указанным идентификатором не найден.
     */
    @Override
    public void updateById(long id, LabWork labWork) {
        Optional<LabWork> oldLabWork = getById(id);
        if (oldLabWork.isEmpty()) {
            throw new EntityNotFoundException("Нет сущности с таким Id");
        }
        removeById(id);
        listOfLabWork.add(labWork);
    }

    /**
     * Возвращает метаданные базы данных, включая размер коллекции.
     *
     * @return Объект DatabaseMetaData с актуальными метаданными.
     */
    @Override
    public DatabaseMetaData getDatabaseMetaData() {
        databaseMetaData.setSize(listOfLabWork.size());
        return databaseMetaData;
    }

    /**
     * Проверяет наличие объекта LabWork с указанным идентификатором в коллекции.
     *
     * @param id Идентификатор объекта LabWork.
     * @return true, если объект с указанным идентификатором существует, иначе false.
     */
    @Override
    public boolean existById(long id) {
        for (LabWork labWork : listOfLabWork) {
            if (labWork.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает объект LabWork с указанным идентификатором, если он существует.
     *
     * @param id Идентификатор объекта LabWork.
     * @return Optional, содержащий объект LabWork, если он найден, иначе пустой Optional.
     */
    @Override
    public Optional<LabWork> getById(long id) {
        for (LabWork labWork : listOfLabWork) {
            if (labWork.getId() == id) {
                return Optional.of(labWork);
            }
        }
        return Optional.empty();
    }

    /**
     * Удаляет объект LabWork с указанным идентификатором. Если объект с таким идентификатором не найден,
     * выбрасывает исключение EntityNotFoundException.
     *
     * @param id Идентификатор объекта LabWork, который необходимо удалить.
     * @return true, если объект был успешно удален, иначе false.
     * @throws EntityNotFoundException Если объект с указанным идентификатором не найден.
     */
    @Override
    public boolean removeById(long id) {
        Optional<LabWork> oldLabWork = getById(id);
        if (oldLabWork.isEmpty()) {
            throw new EntityNotFoundException("Нет сущности с таким Id");
        }
        return listOfLabWork.remove(oldLabWork.get());
    }
}
