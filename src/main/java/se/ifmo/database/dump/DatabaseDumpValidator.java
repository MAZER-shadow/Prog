package se.ifmo.database.dump;

import se.ifmo.creator.LabWorkFieldValidator;
import se.ifmo.database.data.DatabaseMetaData;
import se.ifmo.entity.LabWork;
import se.ifmo.exception.DumpDataBaseValidationException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Валидатор для дампа базы данных, содержащего объекты LabWork.
 * Обеспечивает целостность данных, проверяя DatabaseMetaData и список LabWork.
 */
public class DatabaseDumpValidator {

    private final static String VALID_ERROR = "ошибка валидации поля";
    private final static String NAME_OF_COLLECTION = "LabWork";

    /**
     * Выполняет валидацию всего дампа базы данных.
     *
     * @param dump Дамп базы данных для валидации.
     */
    public void isValidateDatabase(DatabaseDump dump) {
        isValidateDatabaseMetaData(dump.getDatabaseMetaData());
        isValidateListOfLabWork(dump.getListOfLabWork(), dump.getDatabaseMetaData());
    }

    /**
     * Валидирует метаданные базы данных.
     *
     * @param metaData Метаданные для валидации.
     * @throws DumpDataBaseValidationException Если метаданные не проходят валидацию.
     */
    private void isValidateDatabaseMetaData(DatabaseMetaData metaData) {
        if (metaData == null) {
            throw new DumpDataBaseValidationException(String.format("%s %s", VALID_ERROR, "metaData"));
        } else if (metaData.getClazz() == null) {
            throw new DumpDataBaseValidationException(String.format("%s %s", VALID_ERROR, "clazz"));
        } else if (metaData.getLocalDateTime() == null) {
            throw new DumpDataBaseValidationException(String.format("%s %s", VALID_ERROR, "localDateTime"));
        } else if (!metaData.getClazz().equals(NAME_OF_COLLECTION)) {
            throw new DumpDataBaseValidationException(String.format("%s %s", VALID_ERROR, "clazz"));
        } else if (!new LabWorkFieldValidator().isValidDate(String.valueOf(metaData.getLocalDateTime()),
                metaData.getLocalDateTime())) {
            throw new DumpDataBaseValidationException(String.format("%s %s", VALID_ERROR, "localDateTime"));
        }
    }

    /**
     * Валидирует список LabWork, проверяя соответствие размера и отдельные объекты.
     *
     * @param list     Список LabWork для валидации.
     * @param metaData Метаданные базы данных, содержащие информацию о размере коллекции.
     * @throws DumpDataBaseValidationException Если список не проходит валидацию.
     */
    private void isValidateListOfLabWork(List<LabWork> list, DatabaseMetaData metaData) {
        if (list == null) {
            if (metaData.getSize() != 0) {
                throw new DumpDataBaseValidationException(String.format("%s %s", VALID_ERROR, "size"));
            }
        }
        if (metaData.getSize() != list.size()) {
            throw new DumpDataBaseValidationException(String.format("%s %s", VALID_ERROR, "size"));
        }
        Set<Long> setId = new HashSet<>();
        for (LabWork labWork : list) {
            validateLabWork(labWork, setId, metaData.getLocalDateTime());
        }
    }

    /**
     * Валидирует отдельный объект LabWork.
     *
     * @param labWork               Объект LabWork для валидации.
     * @param setId                 Набор идентификаторов для проверки уникальности.
     * @param creationCollectionDate Дата создания коллекции, используется для валидации даты создания LabWork.
     * @throws DumpDataBaseValidationException Если объект LabWork не проходит валидацию.
     */
    private void validateLabWork(LabWork labWork, Set<Long> setId, LocalDate creationCollectionDate) {
        LabWorkFieldValidator validator = new LabWorkFieldValidator();
        if (!validator.isValidName(labWork.getName())) {
            throw new DumpDataBaseValidationException(String.format(
                    "%s %s %d", VALID_ERROR, "name y id =", labWork.getId()));
        } else if (labWork.getCoordinates() == null) {
            throw new DumpDataBaseValidationException(String.format(
                    "%s %s %d", VALID_ERROR, "coordinates y id =", labWork.getId()));
        } else if (!validator.isValidCoordinateX(String.valueOf(labWork.getCoordinates().getX()))) {
            throw new DumpDataBaseValidationException(String.format(
                    "%s %s %d", VALID_ERROR, "coordinateX y id =", labWork.getId()));
        } else if (!validator.isValidCoordinateY(String.valueOf(labWork.getCoordinates().getY()))) {
            throw new DumpDataBaseValidationException(String.format(
                    "%s %s %d", VALID_ERROR, "coordinateY y id =", labWork.getId()));
        } else if (labWork.getCreationDate() == null) {
            throw new DumpDataBaseValidationException(String.format(
                    "%s %s %d", VALID_ERROR, "creationDate y id =", labWork.getId()));
        } else if (!validator.isValidDate(labWork.getCreationDate().toString(), creationCollectionDate)) {
            throw new DumpDataBaseValidationException(String.format(
                    "%s %s %d", VALID_ERROR, "creationDate y id =", labWork.getId()));
        } else if (!validator.isValidMinimalPoint(String.valueOf(labWork.getMinimalPoint()))) {
            throw new DumpDataBaseValidationException(String.format(
                    "%s %s %d", VALID_ERROR, "minimalPoint y id =", labWork.getId()));
        } else if (!validator.isValidMaximumPoint(String.valueOf(labWork.getMaximumPoint()))) {
            throw new DumpDataBaseValidationException(String.format(
                    "%s %s %d", VALID_ERROR, "maximumPoint y id =", labWork.getId()));
        }
    }
}
