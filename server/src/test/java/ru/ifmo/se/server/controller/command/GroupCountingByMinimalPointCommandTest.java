package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseMap;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupCountingByMinimalPointCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private GroupCountingByMinimalPointCommand command;
    private User testUser;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);
        command = new GroupCountingByMinimalPointCommand(labWorkService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithNonEmptyCollection_ReturnsGroupedCounts() {
        // Arrange
        List<LabWork> labWorks = Arrays.asList(
                createLabWorkWithMinimalPoint(50.0),
                createLabWorkWithMinimalPoint(75.0),
                createLabWorkWithMinimalPoint(50.0),
                createLabWorkWithMinimalPoint(100.0),
                createLabWorkWithMinimalPoint(75.0),
                createLabWorkWithMinimalPoint(75.0)
        );

        when(labWorkService.getAll()).thenReturn(labWorks);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertTrue(response instanceof ResponseMap);
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());

        Map<Double, Long> resultMap = ((ResponseMap) response).getResponse();
        assertEquals(3, resultMap.size());
        assertEquals(2L, resultMap.get(50.0));
        assertEquals(3L, resultMap.get(75.0));
        assertEquals(1L, resultMap.get(100.0));
    }

    @Test
    void testExecute_WithEmptyCollection_ReturnsError() {
        // Arrange
        when(labWorkService.getAll()).thenReturn(Collections.emptyList());

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertFalse(response instanceof ResponseMap);
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("В коллекции нет элементов", response.getMessage());
    }

    @Test
    void testExecute_WithSingleElement_ReturnsSingleEntry() {
        // Arrange
        List<LabWork> labWorks = Collections.singletonList(
                createLabWorkWithMinimalPoint(80.0)
        );

        when(labWorkService.getAll()).thenReturn(labWorks);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertTrue(response instanceof ResponseMap);
        Map<Double, Long> resultMap = ((ResponseMap) response).getResponse();
        assertEquals(1, resultMap.size());
        assertEquals(1L, resultMap.get(80.0));
    }

    @Test
    void testExecute_WithAllUniquePoints_ReturnsAllCountsAsOne() {
        // Arrange
        List<LabWork> labWorks = Arrays.asList(
                createLabWorkWithMinimalPoint(10.0),
                createLabWorkWithMinimalPoint(20.0),
                createLabWorkWithMinimalPoint(30.0)
        );

        when(labWorkService.getAll()).thenReturn(labWorks);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertTrue(response instanceof ResponseMap);
        Map<Double, Long> resultMap = ((ResponseMap) response).getResponse();
        assertEquals(3, resultMap.size());
        assertEquals(1L, resultMap.get(10.0));
        assertEquals(1L, resultMap.get(20.0));
        assertEquals(1L, resultMap.get(30.0));
    }

    @Test
    void testExecute_WithAllSamePoints_ReturnsSingleCount() {
        // Arrange
        List<LabWork> labWorks = Arrays.asList(
                createLabWorkWithMinimalPoint(50.0),
                createLabWorkWithMinimalPoint(50.0),
                createLabWorkWithMinimalPoint(50.0)
        );

        when(labWorkService.getAll()).thenReturn(labWorks);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertTrue(response instanceof ResponseMap);
        Map<Double, Long> resultMap = ((ResponseMap) response).getResponse();
        assertEquals(1, resultMap.size());
        assertEquals(3L, resultMap.get(50.0));
    }

    private LabWork createLabWorkWithMinimalPoint(double minimalPoint) {
        LabWork labWork = LabWork.builder().build();
        labWork.setMinimalPoint(minimalPoint);
        return labWork;
    }
}