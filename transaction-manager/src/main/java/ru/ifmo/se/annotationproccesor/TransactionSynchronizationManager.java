package ru.ifmo.se.annotationproccesor;

import java.sql.Connection;

public class TransactionSynchronizationManager {

    private static final ThreadLocal<Connection> RESOURCE_HOLDER = new ThreadLocal<>();

    public static boolean isTransactionActive() {
        return RESOURCE_HOLDER.get() != null;
    }

    public static Connection getCurrentConnection() {
        return RESOURCE_HOLDER.get();
    }

    public static void bindResource(Connection connection) {
        if (isTransactionActive()) {
            throw new IllegalStateException("Cannot bind a new connection when a transaction is already active");
        }
        RESOURCE_HOLDER.set(connection);
    }

    public static void unbindResource() {
        RESOURCE_HOLDER.remove();
    }
}
