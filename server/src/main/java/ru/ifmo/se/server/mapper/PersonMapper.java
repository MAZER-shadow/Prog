package ru.ifmo.se.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ifmo.se.common.dto.model.PersonDto;
import ru.ifmo.se.server.entity.Person;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDto toDto(Person person);
    Person toEntity(PersonDto personDto);
}
