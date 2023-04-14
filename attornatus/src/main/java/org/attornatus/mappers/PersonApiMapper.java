package org.attornatus.mappers;

import org.attornatus.controllers.requests.PersonRequest;
import org.attornatus.models.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonApiMapper {

    Person requestToModel(PersonRequest request);

}
