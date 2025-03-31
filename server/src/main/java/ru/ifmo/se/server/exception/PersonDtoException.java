package ru.ifmo.se.server.exception;

public class PersonDtoException extends RuntimeException {
    public PersonDtoException(String message) {
        super(message);
    }
}
