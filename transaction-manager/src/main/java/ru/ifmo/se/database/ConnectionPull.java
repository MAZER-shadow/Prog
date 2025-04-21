package ru.ifmo.se.database;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.annotationproccesor.TransactionSynchronizationManager;


import java.sql.Connection;
import java.util.LinkedList;
import java.util.Queue;
@Slf4j
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
                log.info("Waiting for a connection... " + Thread.currentThread().getName());
                wait();
                log.info("we got a connection" + Thread.currentThread().getName());
            }
            return queue.poll();
        }
    }

    public synchronized void returnConnection(Connection connection) {
        if (!TransactionSynchronizationManager.isTransactionActive()) {
            queue.add(connection);
            notify();
        }
    }
}
