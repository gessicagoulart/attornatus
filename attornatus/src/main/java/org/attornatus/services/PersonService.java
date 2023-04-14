package org.attornatus.services;

import org.attornatus.controllers.responses.AddressResponse;
import org.attornatus.controllers.responses.PersonResponse;
import org.attornatus.entities.PersonEntity;
import org.attornatus.mappers.PersonJpaMapper;
import org.attornatus.models.Address;
import org.attornatus.models.Person;
import org.attornatus.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonJpaMapper personJpaMapper;

    @Autowired
    AddressService addressService;

    public PersonResponse getPersonById(UUID id) {
        Optional<PersonEntity> personEntity = personRepository.findById(id);
        if (!personEntity.isPresent()) {
            throw new ResponseStatusException(NOT_FOUND, "Person not found. Update will not proceed.");
        }
        return personJpaMapper.entityToResponse(personEntity.get());
    }

    public List<PersonResponse> getAllPersons() {
        List<PersonEntity> personEntities = personRepository.findAll();
        List<PersonResponse> personResponseList = new ArrayList<>(personEntities.size());

        personEntities.forEach(person -> {
            personResponseList.add(personJpaMapper.entityToResponse(person));
        });
        return personResponseList;
    }

    public PersonResponse savePerson(Person person) {
        PersonEntity personEntity = personJpaMapper.modelToEntity(person);

        PersonResponse personResponse = personJpaMapper.entityToResponse(personRepository.save(personEntity));
        mapPersonIdInAddress(person.getAddresses(), personResponse.getId());

        List<AddressResponse> addressResponseList = addressService.saveAllAddresses(person.getAddresses());
        personResponse.setAddresses(addressResponseList);
        return personResponse;
    }

    public PersonResponse updatePerson(UUID id, Person person) {
        Optional<PersonEntity> personEntity = personRepository.findById(id);
        if (!personEntity.isPresent()) {
            throw new ResponseStatusException(NOT_FOUND, "Person not found. Update will not proceed.");
        }

        PersonEntity personFromRequest = personJpaMapper.modelToEntity(person);
        personFromRequest.setId(id);
        mapPersonIdInAddress(person.getAddresses(), id);

        List<AddressResponse> addressResponseList = new ArrayList<>(person.getAddresses().size());
        person.getAddresses()
                .forEach(address -> {
                    if (address.getId() == null) {
                        throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "You must include the address ID to be updated.");
                    }
                    addressResponseList.add(addressService.updateAddress(address.getId(), address));
                });

        PersonResponse personUpdated = personJpaMapper.entityToResponse(personRepository.save(personFromRequest));
        personUpdated.setAddresses(addressResponseList);
        return personUpdated;
    }

    private void mapPersonIdInAddress(List<Address> addressList, UUID personId) {
        if (addressList != null && !addressList.isEmpty()) {
            addressList.forEach(address -> address.setPersonId(personId));
        }
    }
}
