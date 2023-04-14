package org.attornatus.mappers;

import org.attornatus.controllers.responses.PersonResponse;
import org.attornatus.entities.PersonEntity;
import org.attornatus.models.Person;
import org.mapstruct.Mapper;

import java.util.Arrays;

@Mapper(componentModel = "spring", imports = Arrays.class, uses = AddressJpaMapper.class)
public interface PersonJpaMapper {

    PersonEntity modelToEntity(Person person);

    PersonResponse entityToResponse(PersonEntity personResponse);

}
