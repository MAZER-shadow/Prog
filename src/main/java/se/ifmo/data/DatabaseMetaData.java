package se.ifmo.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class DatabaseMetaData {
    private String clazz;
    private LocalDate localDateTime;
    private int size;
}
