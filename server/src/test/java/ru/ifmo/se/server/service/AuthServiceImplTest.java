package ru.ifmo.se.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.dao.UserDao;
import ru.ifmo.se.server.dao.impl.UserDaoImpl;
import ru.ifmo.se.server.exception.AuthenticationRuntimeException;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthServiceImplTest extends AbstractTest {

    AuthServiceImpl authService;
    UserService userService;
    SecretKey secretKey;

    @BeforeEach
    void setUp() {
        // Mock or real UserService implementation would go here
        // For this test, we'll use a simple in-memory implementation
        UserDao userDao = new UserDaoImpl(connectionPull);
        userService = new UserServiceImpl(userDao);
        authService = new AuthServiceImpl(userService, secretKey);
    }

    @Test
    void login_ShouldThrowForInvalidUsername() {
        // Arrange
        String username = "nonExistingUser";
        String password = "somePassword";

        // Act & Assert
        assertThrows(AuthenticationRuntimeException.class, () -> {
            authService.login(username, password);
        });
    }

    @Test
    void login_ShouldThrowForInvalidPassword() {
        // Arrange
        String username = "passwordUser";
        String password = "correctPassword";
        authService.register(username, password);

        // Act & Assert
        assertThrows(AuthenticationRuntimeException.class, () -> {
            authService.login(username, "wrongPassword");
        });
    }



    @Test
    void authenticate_ShouldThrowForEmptyToken() {
        // Act & Assert
        assertThrows(AuthenticationRuntimeException.class, () -> {
            authService.authenticate("");
        });
    }

}