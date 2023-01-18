package com.steve.authenticationService.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationRequest(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password) {
}
