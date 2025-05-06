package ru.ifmo.se.server.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.server.dao.UserDao;
import ru.ifmo.se.server.entity.User;
import ru.ifmo.se.server.AbstractTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class UserDaoImplTest extends AbstractTest {
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl(connectionPull);
    }

    @Test
    void add_shouldAddUserAndReturnWithGeneratedId() {
        // Arrange
        User newUser = User.builder()
                .name("testUser")
                .password("testPassword")
                .build();

        // Act
        User savedUser = userDao.add(newUser);

        // Assert
        assertNotNull(savedUser.getId());
        assertEquals(newUser.getName(), savedUser.getName());
        assertEquals(newUser.getPassword(), savedUser.getPassword());

        // Verify the user was actually saved
        Optional<User> retrievedUser = userDao.getById(savedUser.getId());
        assertTrue(retrievedUser.isPresent());
        assertEquals(savedUser.getId(), retrievedUser.get().getId());
    }

    @Test
    void getById_shouldReturnUserWhenExists() {
        // Arrange
        User testUser = userDao.add(User.builder()
                .name("existingUser")
                .password("existingPass")
                .build());

        // Act
        Optional<User> foundUser = userDao.getById(testUser.getId());

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getId(), foundUser.get().getId());
        assertEquals(testUser.getName(), foundUser.get().getName());
        assertEquals(testUser.getPassword(), foundUser.get().getPassword());
    }

    @Test
    void getById_shouldReturnEmptyOptionalWhenUserNotExists() {
        // Act
        Optional<User> foundUser = userDao.getById(999999L);

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    void updateById_shouldUpdateExistingUser() {
        // Arrange
        User originalUser = userDao.add(User.builder()
                .name("originalName")
                .password("originalPass")
                .build());

        User updatedData = User.builder()
                .name("updatedName")
                .password("updatedPass")
                .build();

        // Act
        userDao.updateById(originalUser.getId(), updatedData);

        // Assert
        Optional<User> updatedUser = userDao.getById(originalUser.getId());
        assertTrue(updatedUser.isPresent());
        assertEquals(originalUser.getId(), updatedUser.get().getId());
        assertEquals(updatedData.getName(), updatedUser.get().getName());
        assertEquals(updatedData.getPassword(), updatedUser.get().getPassword());
    }

    @Test
    void removeById_shouldDeleteUserAndReturnTrueWhenExists() {
        // Arrange
        User userToDelete = userDao.add(User.builder()
                .name("userToDelete")
                .password("passToDelete")
                .build());

        // Act
        boolean deletionResult = userDao.removeById(userToDelete.getId());

        // Assert
        assertTrue(deletionResult);
        assertFalse(userDao.getById(userToDelete.getId()).isPresent());
    }

    @Test
    void removeById_shouldReturnFalseWhenUserNotExists() {
        // Act
        boolean deletionResult = userDao.removeById(999999L);

        // Assert
        assertFalse(deletionResult);
    }

    @Test
    void findByUsername_shouldReturnEmptyOptionalWhenUserNotExists() {
        // Act
        Optional<User> foundUser = userDao.findByUsername("nonExistingUser");

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    void findByUsername_shouldBeCaseSensitive() {
        // Arrange
        String username = "CaseSensitiveUser";
        userDao.add(User.builder()
                .name(username)
                .password("pass")
                .build());

        // Act
        Optional<User> foundWithDifferentCase = userDao.findByUsername(username.toLowerCase());

        // Assert
        assertFalse(foundWithDifferentCase.isPresent());
    }
}