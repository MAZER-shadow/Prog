package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RemoveFirstCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private RemoveFirstCommand command;
    private User testUser;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);
        command = new RemoveFirstCommand(labWorkService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithNonEmptyCollection_RemovesFirstElement() {
        // Arrange
        LabWork firstLabWork = LabWork.builder().build();
        firstLabWork.setId(1L);
        LabWork secondLabWork = LabWork.builder().build();
        secondLabWork.setId(2L);

        List<LabWork> labWorks = Arrays.asList(firstLabWork, secondLabWork);
        when(labWorkService.getAll()).thenReturn(labWorks);
        when(labWorkService.removeById(1L, testUser)).thenReturn(true);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals("Успешное удаление", response.getMessage());
        verify(labWorkService, times(1)).removeById(1L, testUser);
    }

    @Test
    void testExecute_WithEmptyCollection_ReturnsError() {
        // Arrange
        when(labWorkService.getAll()).thenReturn(Collections.emptyList());

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("В коллекции нет сущностей", response.getMessage());
        verify(labWorkService, never()).removeById(anyLong(), any());
    }

    @Test
    void testExecute_WithSingleElement_RemovesIt() {
        // Arrange
        LabWork singleLabWork = LabWork.builder().build();
        singleLabWork.setId(1L);

        List<LabWork> labWorks = Collections.singletonList(singleLabWork);
        when(labWorkService.getAll()).thenReturn(labWorks);
        when(labWorkService.removeById(1L, testUser)).thenReturn(true);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        verify(labWorkService, times(1)).removeById(1L, testUser);
    }

    @Test
    void testExecute_WithNullUser_StillAttemptsRemoval() {
        // Arrange
        LabWork firstLabWork = LabWork.builder().build();
        firstLabWork.setId(1L);

        List<LabWork> labWorks = Collections.singletonList(firstLabWork);
        when(labWorkService.getAll()).thenReturn(labWorks);
        when(labWorkService.removeById(1L, null)).thenReturn(true);

        // Act
        Response response = command.execute(mock(Request.class), null);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        verify(labWorkService, times(1)).removeById(1L, null);
    }

    @Test
    void testExecute_VerifyOrderOfRemoval() {
        // Arrange
        LabWork first = LabWork.builder().build();
        first.setId(1L);
        LabWork second = LabWork.builder().build();
        second.setId(2L);

        List<LabWork> labWorks = Arrays.asList(first, second);
        when(labWorkService.getAll()).thenReturn(labWorks);
        when(labWorkService.removeById(1L, testUser)).thenReturn(true);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        verify(labWorkService, times(1)).removeById(1L, testUser);
        verify(labWorkService, never()).removeById(2L, testUser);
    }
}