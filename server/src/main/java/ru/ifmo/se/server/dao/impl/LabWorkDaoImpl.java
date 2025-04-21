package ru.ifmo.se.server.dao.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.annotationproccesor.TransactionSynchronizationManager;
import ru.ifmo.se.database.ConnectionPull;
import ru.ifmo.se.server.dao.LabWorkDao;
import ru.ifmo.se.server.entity.Coordinates;
import ru.ifmo.se.server.entity.Difficulty;
import ru.ifmo.se.server.entity.LabWork;
import ru.ifmo.se.server.entity.Person;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class LabWorkDaoImpl implements LabWorkDao {
    public static final String TRUNCATE_LAB_WORK = "TRUNCATE lab_work;";
    public static final String FIND_BY_ID = "SELECT lb.*, coordinates.x, coordinates.y, person.name as nam, person.height, person.passport_id" +
            " FROM lab_work AS lb" +
            " JOIN person ON lb.author_id = person.id" +
            " JOIN coordinates ON lb.coordinates_id = coordinates.id" +
            " WHERE lb.id = ?";
    public static final String DELETE_FROM_LAB_WORK_WHERE_ID = "DELETE FROM lab_work WHERE id = ?";
    public static final String SELECT_ALL = "SELECT lb.*, coordinates.x, coordinates.y, person.name as nam, person.height, person.passport_id" +
            " FROM lab_work AS lb" +
            " JOIN person ON lb.author_id = person.id" +
            " JOIN coordinates ON lb.coordinates_id = coordinates.id";
    public static final String INSERT_INTO_LAB_WORK = "INSERT INTO lab_work (name, coordinates_id, creation_date, minimal_point, maximum_point, difficulty, author_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_LAB_WORK = "UPDATE lab_work SET name = ?, coordinates_id = ?, minimal_point = ?, maximum_point = ?, difficulty = ?, author_id = ? WHERE id = ?";

    private final CoordinatesDao coordinatesDao;
    private final PersonDao personDao;
    private ConnectionPull connectionPull;
    public LabWorkDaoImpl(CoordinatesDao coordinatesDao, PersonDao personDao, ConnectionPull connectionPull) {
        this.coordinatesDao = coordinatesDao;
        this.personDao = personDao;
        this.connectionPull = connectionPull;
    }

    @Override
    public void clear() {
        Connection con = connectionPull.getConnection();
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(TRUNCATE_LAB_WORK);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @Override
    public boolean removeById(long id) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(DELETE_FROM_LAB_WORK_WHERE_ID)) {
            stmt.setLong(1, id);
            int s = stmt.executeUpdate();
            return s > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @Override
    public boolean existById(long id) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(FIND_BY_ID);
             Statement stmt1 = con.createStatement()) {
            ResultSet rs1 = stmt1.executeQuery("SELECT txid_current()");
            if (rs1.next()) {
                String txId = rs1.getString(1);
                log.info("[TXID] Current transaction ID: " + txId);
            }
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @Override
    public Optional<LabWork> getById(long id) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(FIND_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LabWork build = LabWork.builder()
                        .name(rs.getString("name"))
                        .id(rs.getLong("id"))
                        .maximumPoint(rs.getFloat("maximum_point"))
                        .minimalPoint(rs.getDouble("minimal_point"))
                        .difficulty(Difficulty.valueOf(rs.getString("difficulty")))
                        .creationDate(rs.getDate("creation_date").toLocalDate())
                        .coordinates(Coordinates.builder()
                                .id(rs.getLong("coordinates_id"))
                                .x(rs.getInt("x"))
                                .y(rs.getLong("y"))
                                .build())
                        .author(Person.builder()
                                .id(rs.getLong("author_id"))
                                .name(rs.getString("nam"))
                                .height(rs.getInt("height"))
                                .passportID(rs.getString("passport_id"))
                                .build())
                        .build();
                return Optional.of(build);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @SneakyThrows
    @Override
    public List<LabWork> getAll() {
        List<LabWork> labWorks = new ArrayList<>();
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(SELECT_ALL);
             Statement stmt1 = con.createStatement()
        ) {
            ResultSet rs = stmt.executeQuery();
            ResultSet rs1 = stmt1.executeQuery("SELECT txid_current()");
            if (rs1.next()) {
                String txId = rs1.getString(1);
                log.info("ID транзакции, в которой происходит выполнение: {}", txId);
            }
            while (rs.next()) {
                LabWork labWork = LabWork.builder()
                        .name(rs.getString("name"))
                        .id(rs.getLong("id"))
                        .maximumPoint(rs.getFloat("maximum_point"))
                        .minimalPoint(rs.getDouble("minimal_point"))
                        .difficulty(Difficulty.valueOf(rs.getString("difficulty")))
                        .creationDate(rs.getDate("creation_date").toLocalDate())
                        .coordinates(Coordinates.builder()
                                .id(rs.getLong("coordinates_id"))
                                .x(rs.getInt("x"))
                                .y(rs.getLong("y"))
                                .build())
                        .author(Person.builder()
                                .id(rs.getLong("author_id"))
                                .name(rs.getString("nam"))
                                .height(rs.getInt("height"))
                                .passportID(rs.getString("passport_id"))
                                .build())
                        .build();
                labWorks.add(labWork);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
        return labWorks;
    }

    @Override
    public DatabaseMetaData getDatabaseMetaData() {
        Connection con = connectionPull.getConnection();
        try {
            return con.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @Override
    public LabWork add(LabWork entity) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(INSERT_INTO_LAB_WORK, Statement.RETURN_GENERATED_KEYS);
             Statement stmt1 = con.createStatement()) {
            Coordinates savedCoordinates = coordinatesDao.add(entity.getCoordinates());
            Person savedAuthor = personDao.add(entity.getAuthor());

            ResultSet rs1 = stmt1.executeQuery("SELECT txid_current()");
            if (rs1.next()) {
                String txId = rs1.getString(1);
                log.info("[TXID] Current transaction ID: {}", txId);
            }

            stmt.setString(1, entity.getName());
            stmt.setLong(2, savedCoordinates.getId());
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.setDouble(4, entity.getMinimalPoint());
            stmt.setFloat(5, entity.getMaximumPoint());
            stmt.setString(6, entity.getDifficulty() == null ? "NORMAL" : entity.getDifficulty().name());
            stmt.setLong(7, savedAuthor.getId());

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    return getById(id).orElseThrow(() -> new RuntimeException("Failed to retrieve saved lab work"));
                } else {
                    throw new SQLException("Creating lab work failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @Override
    public void updateById(long id, LabWork entity) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UPDATE_LAB_WORK)) {
            LabWork labWork = getById(id).orElseThrow(RuntimeException::new);
            coordinatesDao.updateById(labWork.getCoordinates().getId(), entity.getCoordinates());
            personDao.updateById(labWork.getAuthor().getId(), entity.getAuthor());

            stmt.setString(1, entity.getName());
            stmt.setLong(2, labWork.getCoordinates().getId());
            stmt.setDouble(3, entity.getMinimalPoint());
            stmt.setFloat(4, entity.getMaximumPoint());
            stmt.setString(5, entity.getDifficulty().name());
            stmt.setLong(6, labWork.getAuthor().getId());
            stmt.setLong(7, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating lab work failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }
}