package se.ifmo.create;

import se.ifmo.entity.Difficulty;

public class ValidField {
    private static final int MIN_VALUE_OF_X = -436;
    private static final int BOUNDARY_OF_MINIMAL_POINT = 0;
    private static final int BOUNDARY_OF_MAXIMUM_POINT = 0;
    private static final int BOUNDARY_OF_HEIGHT = 0;
    private static final int MAXIMUM_LENGTH_OF_PASSPORT_ID = 25;
    private static final int MINIMUM_LENGTH_OF_PASSPORT_ID = 9;

    public boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    public boolean isValidCoordinateX(String coordinateX) {
        try {
            int intCoordinateX = Integer.parseInt(coordinateX);
            return intCoordinateX >= MIN_VALUE_OF_X;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidCoordinateY(String coordinateY) {
        try {
            Long.parseLong(coordinateY);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidMinimalPoint(String minimalPoint) {
        try {
            double doubleMinimalPoint = Double.parseDouble(minimalPoint);
            return doubleMinimalPoint > BOUNDARY_OF_MINIMAL_POINT;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidMaximumPoint(String maximumPoint) {
        try {
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
            return false;
        }
    }

    public boolean firstValidHeight(String height) {
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