package se.ifmo.create;

import se.ifmo.entity.Difficulty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

public class FieldValidator {
    private static final int MIN_VALUE_OF_X = -436;
    private static final int BOUNDARY_OF_MINIMAL_POINT = 0;
    private static final int BOUNDARY_OF_MAXIMUM_POINT = 0;
    private static final int BOUNDARY_OF_HEIGHT = 0;
    private static final int MAXIMUM_LENGTH_OF_PASSPORT_ID = 25;
    private static final int MINIMUM_LENGTH_OF_PASSPORT_ID = 9;
    private static final int BOUNDARY_OF_ID = 0;
    private final static String DATE_PATTERN = "yyyy-MM-dd";

    public boolean isValidDate(String dateForCheck) {
        try {
            if (dateForCheck == null || dateForCheck.isEmpty()) {
                return false;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            LocalDate date = LocalDate.parse(dateForCheck, formatter);
            if (date.isAfter(LocalDate.now())) {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

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

    public boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

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

    public boolean isValidDifficulty(String difficulty) {
        try {
            Difficulty.valueOf(difficulty);
            return true;
        } catch (IllegalArgumentException e) {
            return difficulty.isEmpty();
        }
    }

    public boolean isValidHeight(String height) {
        try {
            int integerHeight = Integer.parseInt(height);
            return integerHeight > BOUNDARY_OF_HEIGHT;
        } catch (NumberFormatException e) {
            return height.isEmpty();
        }
    }

    public boolean isValidPassportID(String passportID) {
        return passportID.length() <= MAXIMUM_LENGTH_OF_PASSPORT_ID
                && passportID.length() >= MINIMUM_LENGTH_OF_PASSPORT_ID;
    }
}
