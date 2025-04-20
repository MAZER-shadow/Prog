package ru.ifmo.se.server.database.impl;


import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.server.annotationprocces.TransactionSynchronizationManager;
import ru.ifmo.se.server.database.ConnectionPull;
import ru.ifmo.se.server.database.Dao;
import ru.ifmo.se.server.entity.Person;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class PersonDao implements Dao<Person> {
    public static final String DELETE_SQL = "DELETE FROM person WHERE id = ?";
    public static final String FIND_BY_ID_SQL = "SELECT * FROM person WHERE id = ?";
    public static final String INSERT_SQL = "INSERT INTO person (name, height, passport_id) VALUES (?, ?, ?)";
    public static final String UPDATE_SQL = "UPDATE person SET name = ?, height = ?, passport_id = ? WHERE id = ?";
    public static final String FIND_BY_PASSPORT_SQL = "SELECT * FROM person WHERE passport_id = ?";
    private ConnectionPull connectionPull;

    public PersonDao(ConnectionPull connection) {
        connectionPull = connection;
    }

    @Override
    public Person add(Person person) {
        Connection con = connectionPull.getConnection();
        log.info(con.toString());
        try (PreparedStatement stmt = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, person.getName());
            stmt.setInt(2, person.getHeight());
            stmt.setString(3, person.getPassportID());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return Person.builder()
                            .id(rs.getInt(1))
                            .name(person.getName())
                            .height(person.getHeight())
                            .passportID(person.getPassportID())
                            .build();
                }
                throw new RuntimeException("Error getting generated id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding person", e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @Override
    public void updateById(long id, Person person) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, person.getName());
            stmt.setInt(2, person.getHeight());
            stmt.setString(3, person.getPassportID());
            stmt.setLong(4, id);

            if (stmt.executeUpdate() == 0) {
                throw new RuntimeException("Person not found with id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating person", e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @Override
    public boolean removeById(long id) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(DELETE_SQL)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting person", e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @Override
    public Optional<Person> getById(long id) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(FIND_BY_ID_SQL)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(buildPersonFromResultSet(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error getting person", e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    public Optional<Person> getByPassportID(String passportID) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(FIND_BY_PASSPORT_SQL)) {

            stmt.setString(1, passportID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(buildPersonFromResultSet(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding person by passport", e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    private Person buildPersonFromResultSet(ResultSet rs) throws SQLException {
        return Person.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .height(rs.getInt("height"))
                .passportID(rs.getString("passport_id"))
                .build();
    }
}