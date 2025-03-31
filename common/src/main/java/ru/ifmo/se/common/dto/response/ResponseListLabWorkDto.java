package ru.ifmo.se.common.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.ifmo.se.common.dto.model.LabWorkDto;

import java.io.Serializable;
import java.util.List;

@Setter
@SuperBuilder
@Getter
@NoArgsConstructor
public class ResponseListLabWorkDto extends Response implements Serializable {
    private List<LabWorkDto> labWorkList;
}
