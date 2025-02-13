package se.ifmo.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DatabaseMetaData {
    private String clazz;
    private String localDateTime;
    private int size;
}