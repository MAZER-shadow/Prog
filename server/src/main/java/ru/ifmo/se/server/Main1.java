package ru.ifmo.se.server;

import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import ru.ifmo.se.database.ConnectionPull;
import ru.ifmo.se.server.dao.UserDao;
import ru.ifmo.se.server.dao.impl.UserDaoImpl;
import ru.ifmo.se.server.service.AuthServiceImpl;
import ru.ifmo.se.server.service.UserService;
import ru.ifmo.se.server.service.UserServiceImpl;

import java.nio.charset.StandardCharsets;

public class Main1 {
    @SneakyThrows
    public static void main(String[] args) {
        ConnectionPull connectionPull = new ConnectionPull(3);
        UserDao userDao = new UserDaoImpl(connectionPull);
        UserService userService = new UserServiceImpl(userDao);
        String key = "secrettttttttttttttttttttttttttttttttttttttttt";
        AuthServiceImpl authService = new AuthServiceImpl(userService, Keys.hmacShaKeyFor(
                key.getBytes(StandardCharsets.UTF_8)));
//        authService.register("admin", "admin");
        String result = authService.login("admi", "admin");
        System.out.println(authService.authenticate(result));
    }
}
