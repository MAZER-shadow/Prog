package ru.ifmo.se.server.service;

import ru.ifmo.se.annotationproccesor.Transactional;
import ru.ifmo.se.server.entity.User;

import java.util.Optional;

public interface UserService {

    @Transactional
    User save(User user);

    @Transactional
    Optional<User> findById(long id);

    @Transactional
    Optional<User> findByUsername(String username);
}
