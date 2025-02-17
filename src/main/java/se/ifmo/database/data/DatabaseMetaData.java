package se.ifmo.database.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Builder
/**
 * Класс DatabaseMetaData представляет метаданные базы данных, такие как тип коллекции, дата создания и размер.
 * Используется для хранения и предоставления информации о состоянии коллекции.
 */
public class DatabaseMetaData {
    private String clazz;
    private LocalDate localDateTime;
    private int size;

    /**
     * Возвращает строковое представление метаданных базы данных, включая тип коллекции, дату создания и размер.
     *
     * @return Строка, содержащая информацию о типе коллекции, дате создания и размере.
     */
    public String aboutDatabaseMetaData() {
        return "тип коллекции' = " + clazz + '\'' +
                ", дата создания коллекции = " + localDateTime +
                ", размер = " + size;
    }
}
