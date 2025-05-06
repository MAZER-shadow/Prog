package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestLogin;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.AuthService;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationCommandTest extends AbstractTest {

    private AuthService authService;
    private RegistrationCommand command;
    private User testUser;

    @BeforeEach
    void setUp() {
        authService = Mockito.mock(AuthService.class);
        command = new RegistrationCommand(authService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithValidCredentials_ReturnsSuccess() throws AuthenticationException {
        // Arrange
        String username = "newUser";
        String password = "securePass123";
        RequestLogin request = new RequestLogin(username, password);

        doNothing().when(authService).register(username, password);

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals("Вы успешно зарегестрировались", response.getMessage());
        verify(authService, times(1)).register(username, password);
    }

    @Test
    void testExecute_WithExistingUsername_ReturnsError() throws AuthenticationException {
        // Arrange
        String username = "existingUser";
        String password = "pass123";
        RequestLogin request = new RequestLogin(username, password);

        doThrow(new AuthenticationException("User exists"))
                .when(authService).register(username, password);

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("Имя уже занято, извините", response.getMessage());
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

}