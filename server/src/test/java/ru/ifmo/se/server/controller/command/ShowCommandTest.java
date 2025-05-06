package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseListLabWorkDto;
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
import static org.mockito.Mockito.*;

class ShowCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private ShowCommand command;
    private User testUser;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);
        command = new ShowCommand(labWorkService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithNonEmptyCollection_ReturnsAllElements() {
        // Arrange
        LabWork labWork1 = LabWork.builder().build();
        labWork1.setId(1L);
        LabWork labWork2 = LabWork.builder().build();
        labWork2.setId(2L);

        List<LabWork> labWorks = Arrays.asList(labWork1, labWork2);
        when(labWorkService.getAll()).thenReturn(labWorks);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertTrue(response instanceof ResponseListLabWorkDto);
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());

        List<LabWorkDto> resultList = ((ResponseListLabWorkDto) response).getLabWorkList();
        assertEquals(2, resultList.size());
        assertEquals(1L, resultList.get(0).getId());
        assertEquals(2L, resultList.get(1).getId());
    }

    @Test
    void testExecute_WithEmptyCollection_ReturnsEmptyList() {
        // Arrange
        when(labWorkService.getAll()).thenReturn(Collections.emptyList());

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        assertTrue(response instanceof ResponseListLabWorkDto);
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertTrue(((ResponseListLabWorkDto) response).getLabWorkList().isEmpty());
    }

    @Test
    void testExecute_VerifyDtoConversion() {
        // Arrange
        LabWork labWork = LabWork.builder().build();
        labWork.setId(1L);
        labWork.setName("Test Lab");

        List<LabWork> labWorks = Collections.singletonList(labWork);
        when(labWorkService.getAll()).thenReturn(labWorks);

        // Act
        Response response = command.execute(mock(Request.class), testUser);

        // Assert
        LabWorkDto dto = ((ResponseListLabWorkDto) response).getLabWorkList().get(0);
        assertEquals(labWork.getId(), dto.getId());
        assertEquals(labWork.getName(), dto.getName());
    }

    @Test
    void testExecute_WithNullUser_StillReturnsData() {
        // Arrange
        LabWork labWork = LabWork.builder().build();
        labWork.setId(1L);

        List<LabWork> labWorks = Collections.singletonList(labWork);
        when(labWorkService.getAll()).thenReturn(labWorks);

        // Act
        Response response = command.execute(mock(Request.class), null);

        // Assert
        assertEquals(1, ((ResponseListLabWorkDto) response).getLabWorkList().size());
    }

    @Test
    void testExecute_VerifyServiceInteraction() {
        // Arrange
        when(labWorkService.getAll()).thenReturn(Collections.emptyList());

        // Act
        command.execute(mock(Request.class), testUser);

        // Assert
        verify(labWorkService, times(1)).getAll();
    }
}