package ru.ifmo.se.server.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.dao.UserDao;
import ru.ifmo.se.server.dao.impl.UserDaoImpl;
import ru.ifmo.se.server.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class UserServiceImplTest extends AbstractTest {
    private UserServiceImpl userService;
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        // Инициализация UserDao и UserServiceImpl
        userDao = new UserDaoImpl(connectionPull);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    void save_ShouldSaveUserAndReturnIt() {
        // Подготовка тестовых данных
        User user = User.builder()
                .name("testUser")
                .password("testPassword")
                .build();

        // Выполнение тестируемого метода
        User savedUser = userService.save(user);

        // Проверки
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("testUser", savedUser.getName());
        assertEquals("testPassword", savedUser.getPassword());
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        // Подготовка тестовых данных
        User user = User.builder()
                .name("testUser")
                .password("testPassword")
                .build();
        User savedUser = userService.save(user);

        // Выполнение тестируемого метода
        Optional<User> foundUser = userService.findById(savedUser.getId());

        // Проверки
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
        assertEquals("testUser", foundUser.get().getName());
        assertEquals("testPassword", foundUser.get().getPassword());
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenUserDoesNotExist() {
        // Выполнение тестируемого метода с несуществующим ID
        Optional<User> foundUser = userService.findById(999L);

        // Проверка
        assertTrue(foundUser.isEmpty());
    }

    @Test
    void findByUsername_ShouldReturnUser_WhenUserExists() {
        // Подготовка тестовых данных
        User user = User.builder()
                .name("testUser")
                .password("testPassword")
                .build();
        userService.save(user);

        // Выполнение тестируемого метода
        Optional<User> foundUser = userService.findByUsername("testUser");

        // Проверки
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getName());
        assertEquals("testPassword", foundUser.get().getPassword());
    }

    @Test
    void findByUsername_ShouldReturnEmptyOptional_WhenUserDoesNotExist() {
        // Выполнение тестируемого метода с несуществующим именем пользователя
        Optional<User> foundUser = userService.findByUsername("nonExistingUser");

        // Проверка
        assertTrue(foundUser.isEmpty());
    }
}