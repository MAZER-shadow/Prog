package ru.ifmo.se.common.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Setter
@SuperBuilder
@Getter
@NoArgsConstructor
public class Response implements Serializable {
    private boolean status;
    private String message;
}
