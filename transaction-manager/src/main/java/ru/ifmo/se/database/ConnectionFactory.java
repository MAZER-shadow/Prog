package ru.ifmo.se.database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;
import java.util.Properties;

public class ConnectionFactory {
    private static final String url;
    private static final String user;
    private static final String password;

    private static final String NAME_OF_ENV_PATH = "PATHTOCONF";

    static {
        Optional<String> pathToConfig = getConfigPath();
        try {
            InputStream input;
            if (pathToConfig.isPresent()) {
                input = new FileInputStream(pathToConfig.get());
            } else {
                input = ConnectionFactory.class.getClassLoader()
                        .getResourceAsStream("configuration");
            }
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.username");
            password = prop.getProperty("db.password");
            Class.forName(prop.getProperty("db.driver"));
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException("Error connection", e);
        }
    }

    private static Optional<String> getConfigPath() {
        return Optional.ofNullable(System.getProperty("db.config"))
                .or(() -> Optional.ofNullable(System.getenv(NAME_OF_ENV_PATH)));
    }
}
