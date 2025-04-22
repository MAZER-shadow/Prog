package ru.ifmo.se.server.dao;

import ru.ifmo.se.server.entity.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {

    Optional<User> findByUsername(String username);
}
