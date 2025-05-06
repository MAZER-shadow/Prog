package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseLabWorkDto;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MinByMinimalPointCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private MinByMinimalPointCommand command;
    private User testUser;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);
        command = new MinByMinimalPointCommand(labWorkService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithNonEmptyCollection_ReturnsMinimalElement() {
        // Arrange
        LabWork minLabWork = createLabWork(10.0, "Minimal");
        LabWork middleLabWork = createLabWork(50.0, "Middle");
        LabWork maxLabWork = createLabWork(100.0, "Maximal");

        List<LabWork> labWorks = Arrays.asList(maxLabWork, middleLabWork, minLabWork);
        when(labWorkService.getAll()).thenReturn(labWorks);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertTrue(response instanceof ResponseLabWorkDto);
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals(minLabWork.getName(),
                ((ResponseLabWorkDto) response).getLabWorkDto().getName());
        assertEquals(minLabWork.getMinimalPoint(),
                ((ResponseLabWorkDto) response).getLabWorkDto().getMinimalPoint());
    }

    @Test
    void testExecute_WithEmptyCollection_ReturnsError() {
        // Arrange
        when(labWorkService.getAll()).thenReturn(Collections.emptyList());

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("Коллекция пуста", response.getMessage());
    }

    @Test
    void testExecute_WithSingleElement_ReturnsThatElement() {
        // Arrange
        LabWork singleLabWork = createLabWork(30.0, "Single");
        when(labWorkService.getAll()).thenReturn(Collections.singletonList(singleLabWork));

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertTrue(response instanceof ResponseLabWorkDto);
        assertEquals(singleLabWork.getName(),
                ((ResponseLabWorkDto) response).getLabWorkDto().getName());
    }

    @Test
    void testExecute_WithEqualMinimalPoints_ReturnsFirstFound() {
        // Arrange
        LabWork labWork1 = createLabWork(10.0, "First");
        LabWork labWork2 = createLabWork(10.0, "Second");

        List<LabWork> labWorks = Arrays.asList(labWork1, labWork2);
        when(labWorkService.getAll()).thenReturn(labWorks);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertTrue(response instanceof ResponseLabWorkDto);
        assertEquals(labWork1.getName(),
                ((ResponseLabWorkDto) response).getLabWorkDto().getName());
    }

    @Test
    void testExecute_VerifyComparatorLogic() {
        // Arrange
        LabWork labWorkNegative = createLabWork(-10.0, "Negative");
        LabWork labWorkZero = createLabWork(0.0, "Zero");
        LabWork labWorkPositive = createLabWork(10.0, "Positive");

        List<LabWork> labWorks = Arrays.asList(labWorkPositive, labWorkZero, labWorkNegative);
        when(labWorkService.getAll()).thenReturn(labWorks);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertEquals(labWorkNegative.getName(),
                ((ResponseLabWorkDto) response).getLabWorkDto().getName());
    }

    private LabWork createLabWork(double minimalPoint, String name) {
        LabWork labWork = LabWork.builder().build();
        labWork.setMinimalPoint(minimalPoint);
        labWork.setName(name);
        return labWork;
    }
}