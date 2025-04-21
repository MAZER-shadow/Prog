package ru.ifmo.se.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.ifmo.se.common.dto.model.LabWorkDto;

import java.io.Serializable;

@Setter
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestLabWork extends Request implements Serializable {
    private LabWorkDto labWorkDto;
}
