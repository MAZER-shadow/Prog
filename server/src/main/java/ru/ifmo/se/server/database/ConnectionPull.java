package ru.ifmo.se.server.database;

import lombok.SneakyThrows;
import ru.ifmo.se.server.annotationprocces.TransactionSynchronizationManager;


import java.sql.Connection;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPull {
    private Queue<Connection> queue;
    private final int size;
    public ConnectionPull(int size) {
        this.size = size;
        initialize();
    }

    private void initialize() {
        queue = new LinkedList<Connection>();
        for (int i = 0; i < size; i++) {
            queue.add(ConnectionFactory.getConnection());
        }
    }

    @SneakyThrows
    public synchronized Connection getConnection() {
        if (TransactionSynchronizationManager.isTransactionActive()) {
            return TransactionSynchronizationManager.getCurrentConnection();
        } else {
            if (queue.isEmpty()) {
                System.out.println("Waiting for a connection... " + Thread.currentThread().getName());
                wait();
                System.out.println("we got a connection" + Thread.currentThread().getName());
            }
            return queue.poll();
        }
    }

    public synchronized void returnConnection(Connection connection) {
        if (!TransactionSynchronizationManager.isTransactionActive()) {
            queue.add(connection);
            System.out.println("Returned connection" + Thread.currentThread().getName());
            notify();
        }
    }
}
