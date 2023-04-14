package org.attornatus.services;

import org.attornatus.controllers.responses.AddressResponse;
import org.attornatus.controllers.responses.PersonResponse;
import org.attornatus.entities.AddressEntity;
import org.attornatus.mappers.AddressJpaMapper;
import org.attornatus.models.Address;
import org.attornatus.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressJpaMapper addressJpaMapper;

    @Autowired
    PersonService personService;

    public AddressResponse saveAddress(Address address) {
        PersonResponse personEntity = personService.getPersonById(address.getPersonId());
        if (personEntity == null) {
            throw new ResponseStatusException(NOT_FOUND, "Person not found. Address will not be created.");
        }

        validateUniqueMainAddress(address);

        AddressEntity addressEntity = addressJpaMapper.modelToEntity(address);
        return addressJpaMapper.entityToResponse(addressRepository.save(addressEntity));
    }

    public List<AddressResponse> saveAllAddresses(List<Address> addresses) {
        if (addresses != null && !addresses.isEmpty()) {
            List<AddressEntity> addressEntityList =
                    addresses.stream().map(address -> addressJpaMapper.modelToEntity(address)).collect(toList());

            addresses.forEach(this::validateUniqueMainAddress);

            List<AddressEntity> savedAddresses = addressRepository.saveAll(addressEntityList);
            List<AddressResponse> addressResponseList = new ArrayList<>(savedAddresses.size());
            savedAddresses.forEach(addressEntity -> addressResponseList.add(addressJpaMapper.entityToResponse(addressEntity)));
            return addressResponseList;
        }
        return Collections.emptyList();
    }

    public AddressResponse getAddressById(UUID id) {
        Optional<AddressEntity> addressEntity = addressRepository.findById(id);
        if (!addressEntity.isPresent()) {
            throw new ResponseStatusException(NOT_FOUND, "Address not found.");
        }
        return addressJpaMapper.entityToResponse(addressEntity.get());
    }

    public List<AddressResponse> getAllAddresses() {
        List<AddressEntity> addressEntityList = addressRepository.findAll();
        if (addressEntityList.isEmpty()) {
            return Collections.emptyList();
        }
        List<AddressResponse> addressResponses = new ArrayList<>(addressEntityList.size());
        addressEntityList.forEach(address -> {
            AddressResponse addressResponse = addressJpaMapper.entityToResponse(address);
            addressResponses.add(addressResponse);
        });
        return addressResponses;
    }

    public AddressResponse updateAddress(UUID id, Address address) {
        PersonResponse personEntity = personService.getPersonById(address.getPersonId());
        if (personEntity == null) {
            throw new ResponseStatusException(NOT_FOUND, "Person not found. Update will not proceed.");
        }
        Optional<AddressEntity> addressEntity = addressRepository.findById(id);
        validateUniqueMainAddress(address);

        if (!addressEntity.isPresent()) {
            throw new ResponseStatusException(NOT_FOUND, "Address not found. Update will not proceed.");
        }

        AddressEntity addressFromRequest = addressJpaMapper.modelToEntity(address);
        addressFromRequest.setId(id);
        return addressJpaMapper.entityToResponse(addressRepository.save(addressFromRequest));
    }

    private void validateUniqueMainAddress(Address address) {
        if (address.isMainAddress()) {
            List<AddressEntity> addressEntityList = addressRepository.findByPersonId(address.getPersonId());
            Optional<AddressEntity> mainAddressesList = addressEntityList.stream().filter(AddressEntity::isMainAddress).findFirst();
            if (mainAddressesList.isPresent()) {
                AddressEntity previousMainAddress = mainAddressesList.get();
                previousMainAddress.setMainAddress(false);
                addressRepository.save(previousMainAddress);
            }
        }
    }
}
