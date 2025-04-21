package ru.ifmo.se.server.service.validator;

import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.model.PersonDto;
import ru.ifmo.se.server.entity.Difficulty;
import ru.ifmo.se.server.exception.LabWorkDtoException;
import ru.ifmo.se.server.exception.PersonDtoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

/**
 * Класс FieldValidator отвечает за проверку корректности вводимых данных.
 */
public class LabWorkFieldValidator {
    private static final int MIN_VALUE_OF_X = -436;
    private static final int BOUNDARY_OF_MINIMAL_POINT = 0;
    private static final int BOUNDARY_OF_MAXIMUM_POINT = 0;
    private static final int BOUNDARY_OF_HEIGHT = 0;
    private static final int MAXIMUM_LENGTH_OF_PASSPORT_ID = 25;
    private static final int MINIMUM_LENGTH_OF_PASSPORT_ID = 9;
    private static final int BOUNDARY_OF_ID = 0;
    private final static String DATE_PATTERN = "yyyy-MM-dd";
    private final static String VALID_ERROR = "ошибка валидации поля";

    /**
     * Проверяет корректность введённой даты.
     *
     * @param dateForCheck дата в строковом формате.
     * @param dateCollection минимальная допустимая дата.
     * @return true, если дата корректна, иначе false.
     */
    public boolean isValidDate(String dateForCheck, LocalDate dateCollection) {
        try {
            if (dateForCheck == null || dateForCheck.isEmpty()) {
                return false;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            LocalDate date = LocalDate.parse(dateForCheck, formatter);
            if (date.isAfter(LocalDate.now()) || date.isBefore(dateCollection)) {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Проверяет корректность введённого ID.
     *
     * @param id строковое представление ID.
     * @param setId множество существующих ID.
     * @return true, если ID корректен, иначе false.
     */
    public boolean isValidId(String id, Set<Long> setId) {
        try {
            if (id == null || id.isEmpty()) {
                return false;
            }
            long longId = Integer.parseInt(id);
            if (longId <= BOUNDARY_OF_ID || setId.contains(longId)) {
                return false;
            }
            setId.add(longId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Проверяет корректность имени.
     *
     * @param name имя для проверки.
     * @return true, если имя корректно, иначе false.
     */
    public boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    /**
     * Проверяет корректность координаты X.
     *
     * @param coordinateX строковое представление координаты X.
     * @return true, если координата X корректна, иначе false.
     */
    public boolean isValidCoordinateX(String coordinateX) {
        try {
            if (coordinateX == null || coordinateX.isEmpty()) {
                return false;
            }
            int intCoordinateX = Integer.parseInt(coordinateX);
            return intCoordinateX >= MIN_VALUE_OF_X;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Проверяет корректность координаты Y.
     *
     * @param coordinateY строковое представление координаты Y.
     * @return true, если координата Y корректна, иначе false.
     */
    public boolean isValidCoordinateY(String coordinateY) {
        try {
            if (coordinateY == null || coordinateY.isEmpty()) {
                return false;
            }
            Long.parseLong(coordinateY);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Проверяет корректность минимального балла.
     *
     * @param minimalPoint строковое представление минимального балла.
     * @return true, если значение больше установленного порога, иначе false.
     */
    public boolean isValidMinimalPoint(String minimalPoint) {
        try {
            if (minimalPoint == null || minimalPoint.isEmpty()) {
                return false;
            }
            double doubleMinimalPoint = Double.parseDouble(minimalPoint);
            return doubleMinimalPoint > BOUNDARY_OF_MINIMAL_POINT;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Проверяет корректность максимального балла.
     *
     * @param maximumPoint строковое представление максимального балла.
     * @return true, если значение больше установленного порога, иначе false.
     */
    public boolean isValidMaximumPoint(String maximumPoint) {
        try {
            if (maximumPoint == null || maximumPoint.isEmpty()) {
                return false;
            }
            float floatMaximumPoint = Float.parseFloat(maximumPoint);
            return floatMaximumPoint > BOUNDARY_OF_MAXIMUM_POINT;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Проверяет корректность сложности.
     *
     * @param difficulty строковое представление сложности.
     * @return true, если значение соответствует существующему enum, иначе false.
     */
    public boolean isValidDifficulty(String difficulty) {
        try {
            Difficulty.valueOf(difficulty);
            return true;
        } catch (IllegalArgumentException e) {
            return difficulty.isEmpty();
        }
    }

    /**
     * Проверяет корректность роста.
     *
     * @param height строковое представление роста.
     * @return true, если значение больше установленного порога, иначе false.
     */
    public boolean isValidHeight(String height) {
        try {
            int integerHeight = Integer.parseInt(height);
            return integerHeight > BOUNDARY_OF_HEIGHT;
        } catch (NumberFormatException e) {
            return height.isEmpty();
        }
    }

    /**
     * Проверяет корректность паспортного ID.
     *
     * @param passportID строковое представление паспортного ID.
     * @return true, если длина ID находится в допустимом диапазоне, иначе false.
     */
    public boolean isValidPassportID(String passportID) {
        return passportID.length() <= MAXIMUM_LENGTH_OF_PASSPORT_ID
                && passportID.length() >= MINIMUM_LENGTH_OF_PASSPORT_ID;
    }

    public void validateLabWorkDto(LabWorkDto labWork) {
        if (labWork == null) {
            throw new LabWorkDtoException("Ошибка валидации labWork");
        } else if (!isValidName(labWork.getName())) {
            throw new LabWorkDtoException(String.format(
                    "%s %s", VALID_ERROR, "name"));
        } else if (labWork.getCoordinates() == null) {
            throw new LabWorkDtoException(String.format(
                    "%s %s", VALID_ERROR, "coordinates"));
        } else if (!isValidCoordinateX(String.valueOf(labWork.getCoordinates().getX()))) {
            throw new LabWorkDtoException(String.format(
                    "%s %s", VALID_ERROR, "coordinateX"));
        } else if (!isValidCoordinateY(String.valueOf(labWork.getCoordinates().getY()))) {
            throw new LabWorkDtoException(String.format(
                    "%s %s", VALID_ERROR, "coordinateY"));
        } else if (!isValidMinimalPoint(String.valueOf(labWork.getMinimalPoint()))) {
            throw new LabWorkDtoException(String.format(
                    "%s %s", VALID_ERROR, "minimalPoint"));
        } else if (!isValidMaximumPoint(String.valueOf(labWork.getMaximumPoint()))) {
            throw new LabWorkDtoException(String.format(
                    "%s %s", VALID_ERROR, "maximumPoint"));
        }
    }

    public void validatePerson (PersonDto person) {
        if (person == null) {
            throw new PersonDtoException("Ошибка валидации Person");
        } else if (!isValidName(person.getName())) {
            throw new PersonDtoException(String.format("%s %s", VALID_ERROR, "name"));
        } else if (!isValidHeight(String.valueOf(person.getHeight()))) {
            throw new PersonDtoException(String.format("%s %s", VALID_ERROR, "height"));
        } else if (!isValidPassportID(person.getPassportID())) {
            throw new PersonDtoException(String.format("%s %s", VALID_ERROR, "passportID"));
        }
    }
}
