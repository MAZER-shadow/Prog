package ru.ifmo.se.common.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.ifmo.se.common.dto.model.PersonDto;

import java.io.Serializable;

@Setter
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPersonDto extends Request implements Serializable {
    private PersonDto person;
}
