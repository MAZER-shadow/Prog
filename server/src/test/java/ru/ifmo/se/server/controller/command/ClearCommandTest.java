package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClearCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private ClearCommand clearCommand;
    private User testUser;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);
        clearCommand = new ClearCommand(labWorkService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_CallsClearMethodOnService() {
        // Arrange
        Request request = mock(Request.class);

        // Act
        Response response = clearCommand.execute(request, testUser);

        // Assert
        verify(labWorkService, times(1)).clear(testUser);
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals("Коллекция очищена", response.getMessage());
    }

    @Test
    void testExecute_ReturnsSuccessResponse() {
        // Arrange
        Request request = mock(Request.class);

        // Act
        Response response = clearCommand.execute(request, testUser);

        // Assert
        assertNotNull(response);
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals("Коллекция очищена", response.getMessage());
    }

    @Test
    void testExecute_WithNullUser_StillCallsService() {
        // Arrange
        Request request = mock(Request.class);

        // Act
        Response response = clearCommand.execute(request, null);

        // Assert
        verify(labWorkService, times(1)).clear(null);
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
    }
}