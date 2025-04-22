package ru.ifmo.se.server.service;

import ru.ifmo.se.server.entity.User;

import javax.naming.AuthenticationException;

public interface AuthService {
    void register(String name, String password) throws AuthenticationException;
    String login(String name, String password) throws AuthenticationException;
    User authenticate(String token) throws AuthenticationException;
}
