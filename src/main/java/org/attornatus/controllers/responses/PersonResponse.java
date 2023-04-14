package org.attornatus.controllers.responses;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PersonResponse {

    private UUID id;
    private String name;
    private String dateOfBirth;
    private List<AddressResponse> addresses;
}
