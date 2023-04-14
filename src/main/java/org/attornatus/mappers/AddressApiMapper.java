package org.attornatus.mappers;

import org.attornatus.controllers.requests.AddressRequest;
import org.attornatus.models.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressApiMapper {

    Address requestToModel(AddressRequest request);

}
