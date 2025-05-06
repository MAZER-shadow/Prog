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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SortCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private SortCommand command;
    private User testUser;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);
        command = new SortCommand(labWorkService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithNonEmptyCollection_SortsCollection() {
        // Arrange
        LabWork labWork1 = LabWork.builder().build();
        labWork1.setName("B");
        LabWork labWork2 = LabWork.builder().build();
        labWork2.setName("A");
        LabWork labWork3 = LabWork.builder().build();
        labWork3.setName("C");

        List<LabWork> unsortedList = Arrays.asList(labWork1, labWork2, labWork3);
        List<LabWork> sortedList = Arrays.asList(labWork2, labWork1, labWork3);

        when(labWorkService.getSize()).thenReturn(3L);
        when(labWorkService.getAll()).thenReturn(unsortedList);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals("коллекция успешно отсортирована", response.getMessage());
        assertEquals(sortedList, unsortedList); // Проверяем, что коллекция была отсортирована
    }

    @Test
    void testExecute_WithEmptyCollection_ReturnsError() {
        // Arrange
        when(labWorkService.getSize()).thenReturn(0L);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("Нет элементов в коллекции -> нечего сортировать", response.getMessage());
        verify(labWorkService, never()).getAll();
    }

    @Test
    void testExecute_WithSingleElement_ReturnsSuccess() {
        // Arrange
        LabWork singleLabWork = LabWork.builder().build();
        singleLabWork.setName("Single");

        when(labWorkService.getSize()).thenReturn(1L);
        when(labWorkService.getAll()).thenReturn(Collections.singletonList(singleLabWork));

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
    }

    @Test
    void testExecute_VerifyNaturalOrderSorting() {
        // Arrange
        LabWork labWorkA = LabWork.builder().build();
        labWorkA.setName("A");
        LabWork labWorkB = LabWork.builder().build();
        labWorkB.setName("B");
        LabWork labWorkC = LabWork.builder().build();
        labWorkC.setName("C");

        List<LabWork> unsortedList = Arrays.asList(labWorkC, labWorkA, labWorkB);
        List<LabWork> expectedSortedList = Arrays.asList(labWorkA, labWorkB, labWorkC);

        when(labWorkService.getSize()).thenReturn(3L);
        when(labWorkService.getAll()).thenReturn(unsortedList);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertEquals(expectedSortedList, unsortedList);
    }

    @Test
    void testExecute_WithNullUser_StillSorts() {
        // Arrange
        LabWork labWork = LabWork.builder().build();
        labWork.setName("Test");

        when(labWorkService.getSize()).thenReturn(1L);
        when(labWorkService.getAll()).thenReturn(Collections.singletonList(labWork));

        // Act
        Response response = command.execute(mock(Request.class), null);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
    }
}