package ru.ifmo.se.server.service.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.common.dto.model.PersonDto;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.exception.PersonDtoException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LabWorkFieldValidatorTest extends AbstractTest {

    private LabWorkFieldValidator validator;
    private Set<Long> idSet;

    @BeforeEach
    void setUp() {
        validator = new LabWorkFieldValidator();
        idSet = new HashSet<>();
    }


    @Test
    void testIsValidId() {
        String validId = "1";
        String invalidId = "0"; // ID <= 0
        String duplicateId = "1"; // Дубликат
        String emptyId = "";
        String nullId = null;
        String nonNumericId = "abc";

        assertTrue(validator.isValidId(validId, idSet));
        assertFalse(validator.isValidId(invalidId, idSet));
        assertFalse(validator.isValidId(duplicateId, idSet)); // Проверка на дубликат
        assertFalse(validator.isValidId(emptyId, idSet));
        assertFalse(validator.isValidId(nullId, idSet));
        assertFalse(validator.isValidId(nonNumericId, idSet));
    }

    @Test
    void testIsValidName() {
        String validName = "Valid Name";
        String emptyName = "";
        String nullName = null;

        assertTrue(validator.isValidName(validName));
        assertFalse(validator.isValidName(emptyName));
        assertFalse(validator.isValidName(nullName));
    }

    @Test
    void testIsValidCoordinateX() {
        String validX = "-435";
        String invalidX = "-437"; // Ниже MIN_VALUE_OF_X
        String emptyX = "";
        String nullX = null;
        String nonNumericX = "abc";

        assertTrue(validator.isValidCoordinateX(validX));
        assertFalse(validator.isValidCoordinateX(invalidX));
        assertFalse(validator.isValidCoordinateX(emptyX));
        assertFalse(validator.isValidCoordinateX(nullX));
        assertFalse(validator.isValidCoordinateX(nonNumericX));
    }

    @Test
    void testIsValidCoordinateY() {
        String validY = "123";
        String emptyY = "";
        String nullY = null;
        String nonNumericY = "abc";

        assertTrue(validator.isValidCoordinateY(validY));
        assertFalse(validator.isValidCoordinateY(emptyY));
        assertFalse(validator.isValidCoordinateY(nullY));
        assertFalse(validator.isValidCoordinateY(nonNumericY));
    }

    @Test
    void testIsValidMinimalPoint() {
        String validPoint = "1.0";
        String invalidPoint = "0.0"; // Не больше BOUNDARY_OF_MINIMAL_POINT
        String emptyPoint = "";
        String nullPoint = null;
        String nonNumericPoint = "abc";

        assertTrue(validator.isValidMinimalPoint(validPoint));
        assertFalse(validator.isValidMinimalPoint(invalidPoint));
        assertFalse(validator.isValidMinimalPoint(emptyPoint));
        assertFalse(validator.isValidMinimalPoint(nullPoint));
        assertFalse(validator.isValidMinimalPoint(nonNumericPoint));
    }

    @Test
    void testIsValidMaximumPoint() {
        String validPoint = "1.0";
        String invalidPoint = "0.0"; // Не больше BOUNDARY_OF_MAXIMUM_POINT
        String emptyPoint = "";
        String nullPoint = null;
        String nonNumericPoint = "abc";

        assertTrue(validator.isValidMaximumPoint(validPoint));
        assertFalse(validator.isValidMaximumPoint(invalidPoint));
        assertFalse(validator.isValidMaximumPoint(emptyPoint));
        assertFalse(validator.isValidMaximumPoint(nullPoint));
        assertFalse(validator.isValidMaximumPoint(nonNumericPoint));
    }

    @Test
    void testIsValidHeight() {
        String validHeight = "180";
        String invalidHeight = "0"; // Не больше BOUNDARY_OF_HEIGHT
        String emptyHeight = ""; // Допустимо, согласно логике метода
        String nullHeight = null;
        String nonNumericHeight = "abc";

        assertTrue(validator.isValidHeight(validHeight));
        assertFalse(validator.isValidHeight(invalidHeight));
        assertTrue(validator.isValidHeight(emptyHeight));
        assertFalse(validator.isValidHeight(nonNumericHeight));
    }

    @Test
    void testIsValidPassportID() {
        String validPassportID = "123456789"; // MINIMUM_LENGTH_OF_PASSPORT_ID
        String invalidShortPassportID = "12345678"; // Короче минимума
        String invalidLongPassportID = "123456789012345678901234567890"; // Длиннее максимума
        String emptyPassportID = "";

        assertTrue(validator.isValidPassportID(validPassportID));
        assertFalse(validator.isValidPassportID(invalidShortPassportID));
        assertFalse(validator.isValidPassportID(invalidLongPassportID));
        assertFalse(validator.isValidPassportID(emptyPassportID));
    }

    @Test
    void testValidatePerson() {
        PersonDto validPerson = PersonDto.builder()
                .name("Valid Name")
                .height(180)
                .passportID("123456789")
                .build();

        PersonDto invalidPerson = PersonDto.builder()
                .name("")
                .height(0)
                .passportID("123")
                .build();

        assertDoesNotThrow(() -> validator.validatePerson(validPerson));

        assertThrows(PersonDtoException.class, () -> validator.validatePerson(null));
        assertThrows(PersonDtoException.class, () -> validator.validatePerson(invalidPerson));
    }
}