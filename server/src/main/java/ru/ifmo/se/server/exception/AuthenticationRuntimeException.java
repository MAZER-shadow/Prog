package ru.ifmo.se.server.exception;

public class AuthenticationRuntimeException extends RuntimeException {
    public AuthenticationRuntimeException(String message) {
        super(message);
    }
}
