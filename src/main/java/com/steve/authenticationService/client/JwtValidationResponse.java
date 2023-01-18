package com.steve.authenticationService.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtValidationResponse(@JsonProperty("valid") Boolean valid) {
}
