package ru.ifmo.se.common.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.ifmo.se.common.util.AnswerType;

import java.io.Serializable;

@Setter
@SuperBuilder
@Getter
@NoArgsConstructor
public class Response implements Serializable {
    private AnswerType answerType;
    private String message;
}
