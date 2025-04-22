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
public class ResponseToken extends Response implements Serializable {
    private String token;
}
