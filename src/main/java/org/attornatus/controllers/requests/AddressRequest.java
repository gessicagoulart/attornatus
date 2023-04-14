package org.attornatus.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AddressRequest {

    @JsonProperty
    private UUID id;
    @JsonProperty
    private UUID personId;
    @JsonProperty
    @NotNull(message = "Address - street must not be null.")
    private String street;
    @JsonProperty
    @NotNull(message = "Address - post code must not be null.")
    private String postCode;
    @JsonProperty
    @NotNull(message = "Address - number must not be null.")
    private String number;
    @JsonProperty
    private boolean isMainAddress;
    @JsonProperty
    @NotNull(message = "Address - city must not be null.")
    private String city;
}
