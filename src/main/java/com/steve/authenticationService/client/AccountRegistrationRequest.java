package com.steve.authenticationService.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountRegistrationRequest(
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password) {
}
