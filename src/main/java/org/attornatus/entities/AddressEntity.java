package org.attornatus.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static org.attornatus.entities.constants.Constants.ADDRESS_TABLE;
import static org.attornatus.entities.constants.Constants.SCHEMA_ATTORNATUS;

@Entity
@Table(schema = SCHEMA_ATTORNATUS, name = ADDRESS_TABLE)
@Data
public class AddressEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "person_id")
    private UUID personId;

    @Column(name = "street")
    private String street;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "number")
    private String number;

    @Column(name = "city")
    private String city;

    @Column(name = "is_main_address")
    private boolean isMainAddress;
}
