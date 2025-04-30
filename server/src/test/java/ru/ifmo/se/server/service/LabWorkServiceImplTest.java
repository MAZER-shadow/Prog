package ru.ifmo.se.server.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.server.AbstractTest;
import ru.ifmo.se.server.dao.CoordinatesDao;
import ru.ifmo.se.server.dao.LabWorkDao;
import ru.ifmo.se.server.dao.PersonDao;
import ru.ifmo.se.server.dao.UserDao;
import ru.ifmo.se.server.dao.impl.CoordinatesDaoImpl;
import ru.ifmo.se.server.dao.impl.LabWorkDaoImpl;
import ru.ifmo.se.server.dao.impl.PersonDaoImpl;
import ru.ifmo.se.server.dao.impl.UserDaoImpl;
import ru.ifmo.se.server.entity.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class LabWorkServiceImplTest extends AbstractTest {
    private LabWorkService labWorkService;
    private User testUser;
    LabWorkDao labWorkDao;
    @BeforeEach
    void setUp() {
        PersonDao personDao = new PersonDaoImpl(connectionPull);
        CoordinatesDao coordinatesDao = new CoordinatesDaoImpl(connectionPull);
        labWorkDao = new LabWorkDaoImpl(coordinatesDao, personDao, connectionPull);
        labWorkService = new LabWorkServiceImpl(labWorkDao);
        UserDao userDao = new UserDaoImpl(connectionPull);

        // Создаем тестового пользователя
        testUser = User.builder()
                .name("testUser")
                .password("password")
                .build();
        testUser = userDao.add(testUser);
    }

    private LabWork createTestLabWork() {
        Coordinates coordinates = Coordinates.builder()
                .x(10)
                .y(20L)
                .build();

        Person author = Person.builder()
                .name("Test Author")
                .height(180)
                .passportID("AB123456")
                .build();

        return LabWork.builder()
                .name("Test LabWork")
                .coordinates(coordinates)
                .creationDate(LocalDate.now())
                .minimalPoint(85.5)
                .maximumPoint(100.0F)
                .difficulty(Difficulty.HARD)
                .author(author)
                .user(testUser)
                .build();
    }

    @Test
    void add_shouldAddLabWorkAndReturnIt() {
        LabWork labWork = createTestLabWork();
        LabWork addedLabWork = labWorkService.add(labWork);

        assertNotNull(addedLabWork);
        assertNotNull(addedLabWork.getId());
        assertEquals(labWork.getName(), addedLabWork.getName());
    }

    @Test
    void clear_shouldRemoveAllLabWorksForUser() {
        LabWork labWork = createTestLabWork();
        labWorkService.add(labWork);

        assertEquals(1, labWorkService.getSize());

        labWorkService.clear(testUser);

        assertEquals(0, labWorkService.getSize());
    }

    @Test
    void getAll_shouldReturnAllLabWorks() {
        LabWork labWork1 = createTestLabWork();
        LabWork labWork2 = createTestLabWork();

        labWorkService.add(labWork1);
        labWorkService.add(labWork2);

        List<LabWork> labWorks = labWorkService.getAll();
        assertEquals(2, labWorks.size());
    }

    @Test
    void getSize_shouldReturnCorrectSize() {
        assertEquals(0, labWorkService.getSize());

        labWorkService.add(createTestLabWork());

        assertEquals(1, labWorkService.getSize());
    }

    @Test
    void existById_shouldReturnTrueForExistingId() {
        LabWork labWork = createTestLabWork();
        labWork = labWorkService.add(labWork);

        assertTrue(labWorkService.existById(labWork.getId()));
    }

    @Test
    void existById_shouldReturnFalseForNonExistingId() {
        assertFalse(labWorkService.existById(999L));
    }

    @Test
    void updateById_shouldUpdateLabWork() {
        LabWork labWork = createTestLabWork();
        LabWork labWorkSaved = labWorkService.add(labWork);
        LabWork updatedLabWork = createTestLabWork();
        updatedLabWork.setName("Updated Name");

        labWorkService.updateById(labWorkSaved.getId(), updatedLabWork, testUser);

        LabWork retrievedLabWork = labWorkService.getAll().stream()
                .filter(lw -> lw.getId() == labWorkSaved.getId())
                .findFirst()
                .orElse(null);

        assertNotNull(retrievedLabWork);
        assertEquals("Updated Name", retrievedLabWork.getName());
    }

    @Test
    void removeById_shouldReturnTrueAndRemoveLabWork() {
        LabWork labWork = createTestLabWork();
        labWork = labWorkService.add(labWork);

        boolean result = labWorkService.removeById(labWork.getId(), testUser);

        assertTrue(result);
        assertFalse(labWorkService.existById(labWork.getId()));
    }

    @Test
    void removeById_shouldReturnFalseForNonExistingId() {
        boolean result = labWorkService.removeById(999L, testUser);
        assertFalse(result);
    }
}