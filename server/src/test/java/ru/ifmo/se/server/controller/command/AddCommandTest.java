package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.model.CoordinatesDto;
import ru.ifmo.se.common.dto.model.DifficultyDto;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.model.PersonDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestLabWork;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseLabWorkDto;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.exception.LabWorkDtoException;
import ru.ifmo.se.server.mapper.LabWorkMapper;
import ru.ifmo.se.server.service.LabWorkService;
import ru.ifmo.se.server.service.validator.LabWorkFieldValidator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private AddCommand addCommand;
    private User testUser;
    private LabWorkFieldValidator validator;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);
        validator = Mockito.mock(LabWorkFieldValidator.class);
        addCommand = new AddCommand(labWorkService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithValidLabWorkDto_ReturnsSuccessResponse() throws LabWorkDtoException {
        // Arrange
        LabWorkDto labWorkDto = createValidLabWorkDto();
        RequestLabWork request = new RequestLabWork(labWorkDto);
        LabWork savedLabWork = LabWorkMapper.INSTANCE.toEntity(labWorkDto);
        savedLabWork.setId(1L);

        when(labWorkService.add(any(LabWork.class))).thenReturn(savedLabWork);
        doNothing().when(validator).validateLabWorkDto(any(LabWorkDto.class));

        // Act
        Response response = addCommand.execute(request, testUser);

        // Assert
        assertTrue(response instanceof ResponseLabWorkDto);
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertNotNull(((ResponseLabWorkDto) response).getLabWorkDto());
        assertEquals(savedLabWork.getId(), ((ResponseLabWorkDto) response).getLabWorkDto().getId());

        verify(labWorkService, times(1)).add(any(LabWork.class));
    }

    @Test
    void testExecute_WithInvalidLabWorkDto_ReturnsErrorResponse() throws LabWorkDtoException {
        // Arrange
        LabWorkDto labWorkDto = createValidLabWorkDto();
        labWorkDto.setName(""); // Invalid name
        RequestLabWork request = new RequestLabWork(labWorkDto);

        doThrow(new LabWorkDtoException("Invalid name")).when(validator).validateLabWorkDto(any(LabWorkDto.class));

        // Act
        Response response = addCommand.execute(request, testUser);

        // Assert
        assertFalse(response instanceof ResponseLabWorkDto);
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertNotNull(response.getMessage());

        verify(labWorkService, never()).add(any(LabWork.class));
    }

    @Test
    void testExecute_WithInvalidRequestType_ReturnsErrorResponse() {
        // Arrange
        Request invalidRequest = new Request() {}; // Anonymous class for testing

        // Act
        Response response = addCommand.execute(invalidRequest, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        verify(labWorkService, never()).add(any(LabWork.class));
    }

    private LabWorkDto createValidLabWorkDto() {
        return LabWorkDto.builder()
                .name("Valid Lab Work")
                .coordinates(CoordinatesDto.builder()
                        .x(1)
                        .y(1)
                        .build())
                .creationDate(LocalDate.now())
                .minimalPoint(50.0)
                .maximumPoint(100.0f)
                .difficulty(DifficultyDto.NORMAL)
                .author(PersonDto.builder()
                        .name("John Doe")
                        .height(180)
                        .passportID("AB123456")
                        .build())
                .build();
    }
}