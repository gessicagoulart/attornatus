package org.attornatus.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PersonRequest {

    @JsonProperty
    @NotNull(message = "Person - name must not be null.")
    private String name;
    @JsonProperty
    @NotNull(message = "Person - date of birth must not be null.")
    private String dateOfBirth;
    @JsonProperty
    @Valid
    private List<AddressRequest> addresses;
}
