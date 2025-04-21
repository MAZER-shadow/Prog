package ru.ifmo.se.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Setter
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {
    private String commandName;
}
