package ru.ifmo.se.common.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.ifmo.se.common.dto.model.LabWorkDto;

import java.io.Serializable;

@Setter
@SuperBuilder
@Getter
@NoArgsConstructor
public class ResponseLabWorkDto extends Response implements Serializable {
    private LabWorkDto labWorkDto;
}
