package ru.ifmo.se.server.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
@Slf4j
class AuthServiceImplTest {
    @Test
    void fuel(){
        AuthServiceImpl mock = Mockito.mock(AuthServiceImpl.class);
        when(mock.login("123","123")).thenReturn("123");
    }
}