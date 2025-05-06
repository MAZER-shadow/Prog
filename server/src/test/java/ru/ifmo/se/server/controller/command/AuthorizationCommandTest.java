package ru.ifmo.se.server.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ifmo.se.common.dto.request.Request;
import ru.ifmo.se.common.dto.request.RequestLogin;
import ru.ifmo.se.common.dto.response.Response;
import ru.ifmo.se.common.dto.response.ResponseToken;
import ru.ifmo.se.common.util.AnswerType;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.service.AuthService;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorizationCommandTest extends AbstractTest {

    private AuthService authService;
    private AuthorizationCommand command;
    private User testUser;

    @BeforeEach
    void setUp() {
        authService = Mockito.mock(AuthService.class);
        command = new AuthorizationCommand(authService);
        testUser = User.builder()
                .id(1)
                .name("123")
                .password("123")
                .build();
    }

    @Test
    void testExecute_WithValidCredentials_ReturnsToken() throws AuthenticationException {
        // Arrange
        String username = "testUser";
        String password = "testPass";
        String expectedToken = "testToken123";

        RequestLogin request = new RequestLogin(username, password);
        when(authService.login(username, password)).thenReturn(expectedToken);

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertTrue(response instanceof ResponseToken);
        assertEquals(AnswerType.SUCCESS, response.getAnswerType());
        assertEquals("Login Successful", response.getMessage());
        assertEquals(expectedToken, ((ResponseToken) response).getToken());
    }

    @Test
    void testExecute_WithInvalidCredentials_ReturnsError() throws AuthenticationException {
        // Arrange
        String username = "wrongUser";
        String password = "wrongPass";

        RequestLogin request = new RequestLogin(username, password);
        when(authService.login(username, password))
                .thenThrow(new AuthenticationException("Invalid credentials"));

        // Act
        Response response = command.execute(request, testUser);

        // Assert
        assertEquals(AnswerType.ERROR, response.getAnswerType());
        assertEquals("Данные не валидны", response.getMessage());
        assertFalse(response instanceof ResponseToken);
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
    void testExecute_VerifyAuthServiceInteraction() throws AuthenticationException {
        // Arrange
        String username = "testUser";
        String password = "testPass";
        String token = "testToken";

        RequestLogin request = new RequestLogin(username, password);
        when(authService.login(username, password)).thenReturn(token);

        // Act
        command.execute(request, testUser);

        // Assert
        verify(authService, times(1)).login(username, password);
    }

}