package ifmo.se.database.dump;

import ifmo.se.entity.LabWork;
import lombok.Getter;
import lombok.Setter;
import ifmo.se.database.data.DatabaseMetaData;
import ifmo.se.entity.LabWork;

import java.util.List;
@Getter
@Setter
/**
 * Класс DatabaseDump представляет собой контейнер для хранения метаданных базы данных и списка объектов LabWork.
 * Используется для передачи данных между различными компонентами системы, например,
 * для сохранения и восстановления состояния базы данных.
 */
public class DatabaseDump {
    private DatabaseMetaData databaseMetaData;
    private List<LabWork> listOfLabWork;

    /**
     * Конструктор класса DatabaseDump. Инициализирует объект с указанными
     * метаданными базы данных и списком объектов LabWork.
     *
     * @param databaseMetaData Метаданные базы данных, такие как размер коллекции и другие атрибуты.
     * @param listOfLabWork    Список объектов LabWork, которые должны быть сохранены в дампе.
     */
    public DatabaseDump(DatabaseMetaData databaseMetaData, List<LabWork> listOfLabWork) {
        this.databaseMetaData = databaseMetaData;
        this.listOfLabWork = listOfLabWork;
    }
}