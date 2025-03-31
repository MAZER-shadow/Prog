package ru.ifmo.se.common.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Setter
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestIndex extends RequestLabWork implements Serializable {
    private Long index;
}
