package ru.ifmo.se.server.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Builder
@Getter
@ToString
public class User {
    private long id;
    private String name;
    private String password;
}
