package ru.ifmo.se.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ifmo.se.common.dto.model.LabWorkDto;
import ru.ifmo.se.server.entity.LabWork;

@Mapper(uses = {CoordinatesMapper.class, PersonMapper.class})
public interface LabWorkMapper {
    LabWorkMapper INSTANCE = Mappers.getMapper(LabWorkMapper.class);

    @Mapping(target = "coordinates", source = "coordinates")
    @Mapping(target = "author", source = "author")
    LabWorkDto toDto(LabWork labWork);

    @Mapping(target = "coordinates", source = "coordinates")
    @Mapping(target = "author", source = "author")
    LabWork toEntity(LabWorkDto labWorkDto);
}