package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestIndex;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.exception.EntityNotFoundException;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UpdateIdCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private UpdateIdCommand command;
    private User testUser;
    private LabWorkDto testLabWorkDto;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);
        command = new UpdateIdCommand(labWorkService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();

        testLabWorkDto = LabWorkDto.builder().build();
        testLabWorkDto.setName("Updated Lab Work");
    }

    @Test
    void testExecute_WithValidIdAndData_UpdatesSuccessfully() {
        // Arrange
        long validId = 1L;
        RequestIndex request = RequestIndex.builder()
                .index(validId)
                .labWorkDto(testLabWorkDto)
                .build();

        when(labWorkService.existById(eq(validId))).thenReturn(true);
        doNothing().when(labWorkService).updateById(eq(validId), any(LabWork.class), eq(testUser));

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals("успешное обновление сущности", response.getMessage());
        verify(labWorkService, times(1)).updateById(eq(validId), any(LabWork.class), eq(testUser));
    }

    @Test
    void testExecute_WithNonExistingId_ReturnsError() {
        // Arrange
        long nonExistingId = 999L;
        RequestIndex request = RequestIndex.builder()
                .index(nonExistingId)
                .labWorkDto(testLabWorkDto)
                .build();

        when(labWorkService.existById(nonExistingId)).thenReturn(false);

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("в коллекции меньше элементов чем передаваемый индекс", response.getMessage());
        verify(labWorkService, never()).updateById(anyLong(), any(LabWork.class), any());
    }

    @Test
    void testExecute_WithEntityNotFoundException_ReturnsError() {
        // Arrange
        long existingId = 1L;
        RequestIndex request = RequestIndex.builder()
                .index(existingId)
                .labWorkDto(testLabWorkDto)
                .build();

        when(labWorkService.existById(existingId)).thenReturn(true);
        doThrow(new EntityNotFoundException("Сущность не найдена"))
                .when(labWorkService).updateById(eq(existingId), any(LabWork.class), eq(testUser));

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("Сущность не найдена", response.getMessage());
    }

    @Test
    void testExecute_WithWrongRequestType_ReturnsError() {
        // Arrange
        Request wrongRequest = new Request() {}; // Anonymous class

        // Act
        Response response = command.execute(wrongRequest, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
    }

    @Test
    void testExecute_VerifyEntityMappingAndDateUpdate() {
        // Arrange
        long validId = 1L;
        RequestIndex request = RequestIndex.builder()
                .index(validId)
                .labWorkDto(testLabWorkDto)
                .build();

        when(labWorkService.existById(validId)).thenReturn(true);

        // Capture the LabWork argument
        LabWork[] capturedLabWork = new LabWork[1];
        doAnswer(invocation -> {
            capturedLabWork[0] = invocation.getArgument(1);
            return null;
        }).when(labWorkService).updateById(eq(validId), any(LabWork.class), eq(testUser));

        // Act
        command.execute(request, testUser);

        // Assert
        assertNotNull(capturedLabWork[0]);
        assertEquals(validId, capturedLabWork[0].getId());
        assertEquals(testLabWorkDto.getName(), capturedLabWork[0].getName());
        assertEquals(LocalDate.now(), capturedLabWork[0].getCreationDate());
    }

    @Test
    void testExecute_WithNullUser_StillAttemptsUpdate() {
        // Arrange
        long validId = 1L;
        RequestIndex request = RequestIndex.builder()
                .index(validId)
                .labWorkDto(testLabWorkDto)
                .build();

        when(labWorkService.existById(validId)).thenReturn(true);
        doNothing().when(labWorkService).updateById(eq(validId), any(LabWork.class), eq(null));

        // Act
        Response response = command.execute(request, null);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        verify(labWorkService, times(1)).updateById(eq(validId), any(LabWork.class), eq(null));
    }
}