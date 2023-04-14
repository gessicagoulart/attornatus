package org.attornatus.controllers;

import jakarta.validation.Valid;
import org.attornatus.controllers.requests.PersonRequest;
import org.attornatus.controllers.responses.PersonResponse;
import org.attornatus.mappers.PersonApiMapper;
import org.attornatus.models.Person;
import org.attornatus.services.PersonService;
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

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping(value = "/attornatus/v1/person")
public class PersonController {
    @Autowired
    PersonApiMapper personApiMapper;

    @Autowired
    PersonService personService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable UUID id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PersonResponse>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<PersonResponse> savePerson(@RequestBody @Valid PersonRequest personRequest) {
        Person person = personApiMapper.requestToModel(personRequest);
        return ResponseEntity.ok(personService.savePerson(person));
    }

    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable UUID id, @RequestBody @Valid PersonRequest personRequest) {
        Person person = personApiMapper.requestToModel(personRequest);
        return ResponseEntity.ok(personService.updatePerson(id, person));
    }

}
