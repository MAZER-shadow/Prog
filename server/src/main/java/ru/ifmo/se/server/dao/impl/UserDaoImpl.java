package ru.ifmo.se.server.dao.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.annotationproccesor.TransactionSynchronizationManager;
import ru.ifmo.se.database.ConnectionPull;
import ru.ifmo.se.server.dao.UserDao;
import ru.ifmo.se.server.entity.User;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class UserDaoImpl implements UserDao {
    public static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    public static final String FIND_BY_NAME = "SELECT * FROM users WHERE name = ?";
    public static final String DELETE_FROM_USER_WHERE_ID = "DELETE FROM users WHERE id = ?";
    public static final String SELECT_ALL = "SELECT * FROM users";
    public static final String INSERT_INTO_USER = "INSERT INTO users (name, password) VALUES (?, ?)";
    public static final String UPDATE_USER = "UPDATE users SET name = ?, password = ? WHERE id = ?";

    private final ConnectionPull connectionPull;

    public UserDaoImpl(ConnectionPull connectionPull) {
        this.connectionPull = connectionPull;
    }

    @Override
    public User add(User entity) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(INSERT_INTO_USER, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getPassword());

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    return getById(id).orElseThrow(() -> new RuntimeException("Failed to retrieve saved user"));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
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
    public void updateById(long id, User entity) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(UPDATE_USER)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getPassword());
            stmt.setLong(3, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
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
        try (PreparedStatement stmt = con.prepareStatement(DELETE_FROM_USER_WHERE_ID)) {
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (!TransactionSynchronizationManager.isTransactionActive()) {
                connectionPull.returnConnection(con);
            }
        }
    }

    @Override
    public Optional<User> getById(long id) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(FIND_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .password(rs.getString("password"))
                        .build();
                return Optional.of(user);
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

    @Override
    public Optional<User> findByUsername(String name) {
        Connection con = connectionPull.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(FIND_BY_NAME)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .password(rs.getString("password"))
                        .build();
                return Optional.of(user);
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