package org.attornatus.mappers;

import org.attornatus.controllers.responses.AddressResponse;
import org.attornatus.entities.AddressEntity;
import org.attornatus.models.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressJpaMapper {
    AddressEntity modelToEntity(Address address);

    AddressResponse entityToResponse(AddressEntity addressEntity);


}
