package org.attornatus.controllers;

import jakarta.validation.Valid;
import org.attornatus.controllers.requests.AddressRequest;
import org.attornatus.controllers.responses.AddressResponse;
import org.attornatus.mappers.AddressApiMapper;
import org.attornatus.models.Address;
import org.attornatus.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@Validated
@RequestMapping(value = "/attornatus/v1/address")
public class AddressController {

    @Autowired
    AddressApiMapper addressApiMapper;

    @Autowired
    AddressService addressService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable UUID id) {
        AddressResponse addressResponse = addressService.getAddressById(id);
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<AddressResponse>> getAddresses() {
        List<AddressResponse> addressResponses = addressService.getAllAddresses();
        return ResponseEntity.ok(addressResponses);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddressResponse> createAddress(@RequestBody @Valid AddressRequest addressRequest) {
        validatePersonId(addressRequest);
        Address address = addressApiMapper.requestToModel(addressRequest);
        return ResponseEntity.ok(addressService.saveAddress(address));
    }

    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable UUID id, @RequestBody @Valid AddressRequest addressRequest) {
        validatePersonId(addressRequest);
        Address address = addressApiMapper.requestToModel(addressRequest);
        return ResponseEntity.ok(addressService.updateAddress(id, address));
    }

    private void validatePersonId(AddressRequest request) {
        if (request.getPersonId() == null) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Address - person id must not be null.");
        }
    }
}
