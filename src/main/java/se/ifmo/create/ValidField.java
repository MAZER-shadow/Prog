package se.ifmo.create;

import se.ifmo.entity.Difficulty;

public class ValidField {

    public boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    public boolean isValidCoordinateX(String coordinateX) {
        try {
            int intCoordinateX = Integer.parseInt(coordinateX);
            return intCoordinateX > -437;
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
            return doubleMinimalPoint > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidMaximumPoint(String maximumPoint) {
        try {
            Float floatMaximumPoint = Float.parseFloat(maximumPoint);
            return floatMaximumPoint > 0;
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
            Integer integerHeight = Integer.parseInt(height);
            return integerHeight > 0;
        } catch (NumberFormatException e) {
            return height.isEmpty();
        }
    }

    public boolean secondValidHeight(String height) {
        return !height.isEmpty();
    }

    public boolean isValidPassportID(String passportID) {
        return passportID.length() < 26 && passportID.length() > 8;
    }
}