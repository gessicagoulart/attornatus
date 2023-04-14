package org.attornatus.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.attornatus.entities.constants.Constants.PERSON_TABLE;
import static org.attornatus.entities.constants.Constants.SCHEMA_ATTORNATUS;

@Entity
@Table(schema = SCHEMA_ATTORNATUS, name = PERSON_TABLE)
@Data
public class PersonEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @OneToMany(mappedBy = "personId", fetch = FetchType.LAZY)
    private List<AddressEntity> addresses = new ArrayList<>();

}
