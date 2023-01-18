package com.steve.authenticationService.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationError(@JsonProperty("message") String message) {
}
