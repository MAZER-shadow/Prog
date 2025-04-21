package ru.ifmo.se.server.dao.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.annotationproccesor.TransactionSynchronizationManager;
import ru.ifmo.se.database.ConnectionPull;
import ru.ifmo.se.server.dao.Dao;
import ru.ifmo.se.server.entity.Coordinates;

import java.sql.*;
import java.util.Optional;
@Slf4j
public class CoordinatesDao implements Dao<Coordinates> {
    public static final String DELETE_FROM_COORDINATES_WHERE_ID = "DELETE FROM coordinates WHERE id = ?";
    public static final String FIND_BY_ID = "SELECT * FROM coordinates WHERE id = ?";
    public static final String ADD_FROM_COORDINATES = "INSERT INTO coordinates (x, y) VALUES (?, ?)";
    public static final String UPDATE_COORDINATES = "UPDATE coordinates SET x = ?, y = ? WHERE id = ?";
    private ConnectionPull connectionPull;

    public CoordinatesDao(ConnectionPull connectionPull) {
        this.connectionPull = connectionPull;
    }

    @Override
    public Coordinates add(Coordinates coordinates) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(ADD_FROM_COORDINATES, Statement.RETURN_GENERATED_KEYS);
             Statement stmt1 = con.createStatement()) {

            ResultSet rs1 = stmt1.executeQuery("SELECT txid_current()");
            if (rs1.next()) {
                String txId = rs1.getString(1);
                log.info("[TXID] Current transaction ID: {}", txId);
            }

            stmt.setLong(1, coordinates.getX());
            stmt.setLong(2, coordinates.getY());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            coordinates.setId(id);
            return coordinates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @Override
    public void updateById(long id, Coordinates coordinates) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UPDATE_COORDINATES)) {
            stmt.setLong(1, coordinates.getX());
            stmt.setLong(2, coordinates.getY());
            stmt.setLong(3, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("No coordinates found with id: " + id);
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
    public boolean removeById(long id) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(DELETE_FROM_COORDINATES_WHERE_ID)) {
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
    public Optional<Coordinates> getById(long id) {
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
            if (rs.next()) {
                Coordinates coordinates = Coordinates.builder()
                        .id(rs.getInt("id"))
                        .x(rs.getInt("x"))
                        .y(rs.getLong("y"))
                        .build();
                return Optional.of(coordinates);
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
}
