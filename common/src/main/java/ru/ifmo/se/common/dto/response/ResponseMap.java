package ru.ifmo.se.common.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Map;

@Setter
@SuperBuilder
@Getter
@NoArgsConstructor
public class ResponseMap extends Response implements Serializable {
    Map<Double, Long> response;
}
