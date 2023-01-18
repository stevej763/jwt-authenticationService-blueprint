package com.steve.authenticationService.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(@JsonProperty("accessKey") String token,
                                     @JsonProperty("success") boolean success,
                                     @JsonProperty("errors") AuthenticationError error) {

    public AuthenticationResponse(@JsonProperty("success") boolean success, @JsonProperty("errors") AuthenticationError error) {
        this(null, success, error);
    }

    public AuthenticationResponse(@JsonProperty("accessKey") String token, @JsonProperty("success") boolean success) {
        this(token, success, null);
    }
}
