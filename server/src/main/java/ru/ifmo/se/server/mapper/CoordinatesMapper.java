package ru.ifmo.se.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.ifmo.se.common.dto.model.CoordinatesDto;
import ru.ifmo.se.server.entity.Coordinates;

@Mapper
public interface CoordinatesMapper {
    CoordinatesMapper INSTANCE = Mappers.getMapper(CoordinatesMapper.class);

    CoordinatesDto toDto(Coordinates coordinates);
    Coordinates toEntity(CoordinatesDto coordinatesDto);
}
