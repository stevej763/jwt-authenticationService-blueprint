package com.steve.authenticationService.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtValidationRequest(@JsonProperty("accessKey") String accessKey) {


}
