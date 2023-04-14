package org.attornatus.models;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Person {

    private UUID id;
    private String name;
    private String dateOfBirth;
    private List<Address> addresses;
}
