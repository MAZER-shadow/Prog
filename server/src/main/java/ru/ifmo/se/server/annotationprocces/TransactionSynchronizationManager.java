package ru.ifmo.se.server.annotationprocces;

import java.sql.Connection;

public class TransactionSynchronizationManager {

    private static final ThreadLocal<Connection> resourceHolder = new ThreadLocal<>();

    public static boolean isTransactionActive() {
        return resourceHolder.get() != null;
    }

    public static Connection getCurrentConnection() {
        return resourceHolder.get();
    }

    public static void bindResource(Connection connection) {
        if (isTransactionActive()) {
            throw new IllegalStateException("Cannot bind a new connection when a transaction is already active");
        }
        resourceHolder.set(connection);
    }

    public static void unbindResource() {
        resourceHolder.remove();
    }
}
