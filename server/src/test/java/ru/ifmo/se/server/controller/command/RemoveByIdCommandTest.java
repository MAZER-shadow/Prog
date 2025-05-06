package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestId;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.exception.EntityNotFoundException;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveByIdCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private RemoveByIdCommand command;
    private User testUser;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);
        command = new RemoveByIdCommand(labWorkService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithValidId_ReturnsSuccess() {
        // Arrange
        long validId = 1L;
        RequestId request = new RequestId(validId);

        when(labWorkService.existById(validId)).thenReturn(true);
        when(labWorkService.removeById(validId, testUser)).thenReturn(true);

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals("успешное удаление сущности", response.getMessage());
        verify(labWorkService, times(1)).removeById(validId, testUser);
    }

    @Test
    void testExecute_WithNonExistingId_ReturnsError() {
        // Arrange
        long nonExistingId = 999L;
        RequestId request = new RequestId(nonExistingId);

        when(labWorkService.existById(nonExistingId)).thenReturn(false);

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("Нет такого id", response.getMessage());
        verify(labWorkService, never()).removeById(anyLong(), any());
    }

    @Test
    void testExecute_WithEntityNotFoundException_ReturnsError() {
        // Arrange
        long existingId = 1L;
        RequestId request = new RequestId(existingId);

        when(labWorkService.existById(existingId)).thenReturn(true);
        when(labWorkService.removeById(existingId, testUser))
                .thenThrow(new EntityNotFoundException("Сущность не найдена"));

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("Сущность не найдена", response.getMessage());
        verify(labWorkService, times(1)).removeById(existingId, testUser);
    }

    @Test
    void testExecute_WithWrongRequestType_ReturnsError() {
        // Arrange
        Request wrongRequest = new Request() {}; // Anonymous class

        // Act
        Response response = command.execute(wrongRequest, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        verify(labWorkService, never()).removeById(anyLong(), any());
    }

    @Test
    void testExecute_WithNullUser_StillAttemptsRemoval() {
        // Arrange
        long validId = 1L;
        RequestId request = new RequestId(validId);

        when(labWorkService.existById(validId)).thenReturn(true);
        when(labWorkService.removeById(validId, null)).thenReturn(true);

        // Act
        Response response = command.execute(request, null);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        verify(labWorkService, times(1)).removeById(validId, null);
    }
}