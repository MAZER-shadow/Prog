package ru.ifmo.se.server;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.ifmo.se.database.ConnectionPull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
@Slf4j
public class AbstractTest {
    public static final String TRUNCATE_LAB_WORK = "DELETE FROM lab_work";
    public static final String TRUNCATE_USER = "DELETE FROM users";
    public static final String TRUNCATE_PERSON = "DELETE FROM person";
    public static final String TRUNCATE_COORDINATES = "DELETE FROM coordinates";
    protected static ConnectionPull connectionPull;
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("mydatabase")
            .withUsername("postgres")
            .withPassword("mysecretpassword")
            .withExposedPorts(5432)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(
                            new PortBinding(Ports.Binding.bindPort(5435), new ExposedPort(5432))
                    )
            ));

    @SneakyThrows
    @BeforeAll
    static void setup() {
        // Wait for the container to be ready
        postgresContainer.start();
        Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(), postgresContainer.getUsername(), postgresContainer.getPassword());
        Liquibase
                liquibase = new Liquibase("db-test/changelog-test/db.changelog-master.xml", new ClassLoaderResourceAccessor(),
                new JdbcConnection(connection));
        liquibase.update("");
        connectionPull = new ConnectionPull(10);
    }

    @AfterEach
    void clear() {
        Connection connection = connectionPull.getConnection();
        try (Statement stmt = connection.createStatement();
            Statement stmt2 = connection.createStatement();
            Statement stmt3 = connection.createStatement();
            Statement stmt4 = connection.createStatement()) {
            stmt.executeUpdate(TRUNCATE_LAB_WORK);
            stmt2.executeUpdate(TRUNCATE_USER);
            stmt3.executeUpdate(TRUNCATE_PERSON);
            stmt4.executeUpdate(TRUNCATE_COORDINATES);
            connectionPull.returnConnection(connection);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
