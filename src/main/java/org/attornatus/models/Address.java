package org.attornatus.models;

import lombok.Data;

import java.util.UUID;

@Data
public class Address {

    private UUID id;
    private UUID personId;
    private String street;
    private String postCode;
    private String number;
    private boolean isMainAddress;
    private String city;
}
