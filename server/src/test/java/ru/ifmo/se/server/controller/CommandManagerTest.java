package ru.ifmo.se.server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.io.Writer;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.configuration.CommandConfiguration;
import ru.ifmo.se.server.configuration.Condition;
import ru.ifmo.se.server.controller.command.AbstractCommand;
import ru.ifmo.se.server.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommandManagerTest {

    private CommandManager commandManager;
    private Writer writer;
    private AuthService authService;
    private AbstractCommand mockCommand;
    private AbstractCommand secureCommand;
    private AbstractCommand helpCommand;

    @BeforeEach
    void setUp() {
        writer = mock(Writer.class);
        authService = mock(AuthService.class);
        commandManager = new CommandManager(writer, authService);

        // Создаем мок-команды для тестирования
        mockCommand = mock(AbstractCommand.class);
        when(mockCommand.getName()).thenReturn("test");
        when(mockCommand.getCondition()).thenReturn(Condition.INSECURE);
        when(mockCommand.execute(any(), any())).thenReturn(Response.builder()
                .answerType(AnswerType.SUCCESS)
                .message("Command executed")
                .build());

        secureCommand = mock(AbstractCommand.class);
        when(secureCommand.getName()).thenReturn("secure");
        when(secureCommand.getCondition()).thenReturn(Condition.SECURE);
        when(secureCommand.execute(any(), any())).thenReturn(Response.builder()
                .answerType(AnswerType.SUCCESS)
                .message("Secure command executed")
                .build());

        helpCommand = mock(AbstractCommand.class);
        when(helpCommand.getName()).thenReturn(CommandConfiguration.HELP_NAME);
        when(helpCommand.getCondition()).thenReturn(Condition.INSECURE);
        when(helpCommand.execute(any(), any())).thenReturn(Response.builder()
                .answerType(AnswerType.SUCCESS)
                .message("Help executed")
                .build());

        // Регистрируем команды
        commandManager.register("test", mockCommand);
        commandManager.register("secure", secureCommand);
        commandManager.register(CommandConfiguration.HELP_NAME, helpCommand);
    }

    @Test
    void testExecute_UnsecureCommand_Success() {
        Request request = new Request("test", null);
        Response response = commandManager.execute(request);

        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals("Command executed", response.getMessage());
        verify(mockCommand).execute(request, null);
    }


    @Test
    void testExecute_CommandNotFound_ThrowsException() {
        Request request = new Request("unknown", null);

        Response response = commandManager.execute(request);

        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertTrue(response.getMessage().contains("Не найдено команды: unknown"));
    }



    @Test
    void testExecute_GeneralException_ReturnsError() {
        when(mockCommand.execute(any(), any()))
                .thenThrow(new RuntimeException("Test error"));

        Request request = new Request("test", null);
        Response response = commandManager.execute(request);

        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("Test error", response.getMessage());
    }

    @Test
    void testRegister_CommandAddedToMap() {
        String commandName = "new_command";
        AbstractCommand newCommand = mock(AbstractCommand.class);
        when(newCommand.getName()).thenReturn(commandName);

        commandManager.register(commandName, newCommand);

        assertDoesNotThrow(() -> {
            Request request = new Request(commandName, null);
            commandManager.execute(request);
        });
    }
}