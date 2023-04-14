package org.attornatus.controllers.responses;

import lombok.Data;

import java.util.UUID;

@Data
public class AddressResponse {
    private UUID id;
    private UUID personId;
    private String street;
    private String postCode;
    private String number;
    private boolean isMainAddress;
    private String city;

}
