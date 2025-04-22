package ru.ifmo.se.server.service;

import ru.ifmo.se.server.dao.UserDao;
import ru.ifmo.se.server.entity.User;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User save(User user) {
        return userDao.add(user);
    }

    @Override
    public Optional<User> findById(long id) {
        return userDao.getById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
