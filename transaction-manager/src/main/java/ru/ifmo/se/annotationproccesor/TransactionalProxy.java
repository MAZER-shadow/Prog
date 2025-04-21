package ru.ifmo.se.annotationproccesor;

import lombok.extern.slf4j.Slf4j;
import ru.ifmo.se.database.ConnectionPull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class TransactionalProxy implements InvocationHandler {

    private final Object target;
    private final TransactionManager transactionManager;
    private final ConnectionPull connectionPull;

    public TransactionalProxy(Object target, TransactionManager transactionManager, ConnectionPull connectionPool) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.connectionPull = connectionPool;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean isConnection = TransactionSynchronizationManager.isTransactionActive();
        if (method.isAnnotationPresent(Transactional.class)) {
            Connection connection;
            if (isConnection) {
                connection = TransactionSynchronizationManager.getCurrentConnection();
            } else {
                connection = connectionPull.getConnection();
                TransactionSynchronizationManager.bindResource(connection);
            }

            try {
                transactionManager.beginTransaction(connection);


                Object invoke = method.invoke(target, args);


                transactionManager.commitTransaction(connection);

                return invoke;
            } catch (Exception e) {
                transactionManager.rollbackTransaction(connection);
                throw new RuntimeException(e);
            } finally {
                if (connection != null) {
                    TransactionSynchronizationManager.unbindResource();
                    connectionPull.returnConnection(connection);
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            return method.invoke(target, args);
        }
    }

    public static <T> T newProxyInstance(Object target, TransactionManager transactionManager, ConnectionPull connectionPool, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                new Class<?>[]{interfaceType},
                new TransactionalProxy(target, transactionManager, connectionPool)
        );
    }
}