package com.steve.authenticationService.domain;

public record ValidationResponse(boolean valid, String error) {
    public ValidationResponse(boolean valid) {
        this(valid, null);
    }
}
