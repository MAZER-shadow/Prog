package ru.ifmo.se.annotationproccesor;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    public void beginTransaction(Connection connection) throws SQLException {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        connection.setAutoCommit(false);
    }

    public void commitTransaction(Connection connection) throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.commit();
        }
    }

    public void rollbackTransaction(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                System.err.println("Error rolling back transaction: " + e.getMessage());
            }
        }
    }
}
