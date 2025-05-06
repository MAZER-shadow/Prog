package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestPersonDto;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.Person;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.exception.PersonDtoException;
import ru.ifmo.se.server.mapper.PersonMapper;
import ru.ifmo.se.server.service.LabWorkService;
import ru.ifmo.se.server.service.validator.LabWorkFieldValidator;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountGreaterThanAuthorCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private CountGreaterThanAuthorCommand command;
    private User testUser;
    private LabWorkFieldValidator validator;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);
        validator = Mockito.mock(LabWorkFieldValidator.class);
        command = new CountGreaterThanAuthorCommand(labWorkService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithValidPersonDto_ReturnsCorrectCount() throws PersonDtoException {
        // Arrange
        Person comparisonPerson = Person.builder()
                .name("B")
                .height(180)
                .passportID("AB12345678")
                .build();
        RequestPersonDto request = new RequestPersonDto(PersonMapper.INSTANCE.toDto(comparisonPerson));

        List<LabWork> labWorks = Arrays.asList(
                createLabWorkWithAuthor("A"),
                createLabWorkWithAuthor("B"),
                createLabWorkWithAuthor("C"),
                createLabWorkWithAuthor("D")
        );

        when(labWorkService.getAll()).thenReturn(labWorks);
        doNothing().when(validator).validatePerson(any());

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertTrue(response.getMessage().contains("2")); // Only "C" and "D" are greater than "B"
        verify(labWorkService, times(1)).getAll();
    }

    @Test
    void testExecute_WithInvalidPersonDto_ReturnsError() throws PersonDtoException {
        // Arrange
        RequestPersonDto request = new RequestPersonDto(PersonMapper.INSTANCE.toDto(Person.builder()
                .name("")
                .height(180)
                .passportID("AB12345678")
                .build()));

        doThrow(new PersonDtoException("Invalid name")).when(validator).validatePerson(any());

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertNotNull(response.getMessage());
        verify(labWorkService, never()).getAll();
    }

    @Test
    void testExecute_WithEmptyCollection_ReturnsZero() throws PersonDtoException {
        // Arrange
        Person comparisonPerson = Person.builder()
                .name("B")
                .height(180)
                .passportID("AB12345678")
                .build();
        RequestPersonDto request = new RequestPersonDto(PersonMapper.INSTANCE.toDto(comparisonPerson));

        when(labWorkService.getAll()).thenReturn(List.of());
        doNothing().when(validator).validatePerson(any());

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertTrue(response.getMessage().contains("0"));
    }

    @Test
    void testExecute_WithWrongRequestType_ReturnsError() {
        // Arrange
        Request wrongRequest = new Request() {}; // Anonymous class

        // Act
        Response response = command.execute(wrongRequest, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        verify(labWorkService, never()).getAll();
    }

    @Test
    void testExecute_WithEqualAuthors_ReturnsCorrectCount() throws PersonDtoException {
        // Arrange
        Person comparisonPerson = Person.builder()
                .name("B")
                .height(180)
                .passportID("AB12345678")
                .build();
        RequestPersonDto request = new RequestPersonDto(PersonMapper.INSTANCE.toDto(comparisonPerson));

        List<LabWork> labWorks = Arrays.asList(
                createLabWorkWithAuthor("A"),
                createLabWorkWithAuthor("B"),
                createLabWorkWithAuthor("B"),
                createLabWorkWithAuthor("C")
        );

        when(labWorkService.getAll()).thenReturn(labWorks);
        doNothing().when(validator).validatePerson(any());

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertTrue(response.getMessage().contains("1")); // Only "C" is greater than "B"
    }

    private LabWork createLabWorkWithAuthor(String authorName) {
        LabWork labWork = LabWork.builder().build();
        labWork.setAuthor(Person.builder()
                .name(authorName)
                .height(180)
                .passportID("ID" + authorName)
                .build());
        return labWork;
    }
}