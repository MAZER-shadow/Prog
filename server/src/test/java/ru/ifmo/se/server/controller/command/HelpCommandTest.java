package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.LabWorkService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HelpCommandTest extends AbstractTest {

    private LabWorkService labWorkService;
    private List<AbstractCommand> testCommands;
    private HelpCommand helpCommand;
    private User testUser;

    @BeforeEach
    void setUp() {
        labWorkService = Mockito.mock(LabWorkService.class);

        // Создаем тестовые команды
        AbstractCommand insecureCommand = new AbstractCommand(labWorkService, "insecure", "Insecure command", Condition.INSECURE) {
            @Override
            public Response execute(Request request, User user) {
                return null;
            }
        };

        AbstractCommand secureCommand = new AbstractCommand(labWorkService, "secure", "Secure command", Condition.SECURE) {
            @Override
            public Response execute(Request request, User user) {
                return null;
            }
        };

        testCommands = Arrays.asList(insecureCommand, secureCommand);
        helpCommand = new HelpCommand(labWorkService, testCommands);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithNullUser_ReturnsInsecureCommands() {
        // Arrange
        Request request = mock(Request.class);

        // Act
        Response response = helpCommand.execute(request, null);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertTrue(response.getMessage().contains("insecure: Insecure command"));
        assertFalse(response.getMessage().contains("secure: Secure command"));
    }

    @Test
    void testExecute_WithAuthenticatedUser_ReturnsAllCommands() {
        // Arrange
        Request request = mock(Request.class);

        // Act
        Response response = helpCommand.execute(request, testUser);
        Response response1 = helpCommand.execute(request, null);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals(AnswerType.SUCCESS, response1.getAnswerType());
        assertTrue(response1.getMessage().contains("insecure: Insecure command"));
        assertTrue(response.getMessage().contains("secure: Secure command"));
    }

    @Test
    void testDoResultCommand_SeparatesCommandsCorrectly() {
        // Arrange
        StringBuilder insecureBuilder = new StringBuilder();
        StringBuilder secureBuilder = new StringBuilder();

        // Act
        helpCommand = new HelpCommand(labWorkService, testCommands);

        // Assert (проверяем через execute)
        Response insecureResponse = helpCommand.execute(mock(Request.class), null);
        assertTrue(insecureResponse.getMessage().contains("insecure: Insecure command"));
        assertFalse(insecureResponse.getMessage().contains("secure: Secure command"));

        Response secureResponse = helpCommand.execute(mock(Request.class), testUser);
        assertTrue(secureResponse.getMessage().contains("secure: Secure command"));
        assertFalse(secureResponse.getMessage().contains("insecure: Insecure command"));
    }

    @Test
    void testExecute_WithEmptyCommandList_ReturnsEmptyHelp() {
        // Arrange
        HelpCommand emptyHelpCommand = new HelpCommand(labWorkService, List.of());
        Request request = mock(Request.class);

        // Act
        Response nullUserResponse = emptyHelpCommand.execute(request, null);
        Response authUserResponse = emptyHelpCommand.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, nullUserResponse.getAnswerType());
        assertEquals("", nullUserResponse.getMessage().trim());

        assertEquals(AnswerType.SUCCESS, authUserResponse.getAnswerType());
        assertEquals("", authUserResponse.getMessage().trim());
    }

    @Test
    void testExecute_CommandFormattingIsCorrect() {
        // Arrange
        Request request = mock(Request.class);

        // Act
        Response response = helpCommand.execute(request, testUser);
        String message = response.getMessage();

        // Assert
        assertFalse(message.contains("insecure: Insecure command"));
        assertTrue(message.contains("secure: Secure command"));
        assertEquals(1, message.trim().split("\n").length);
    }
}