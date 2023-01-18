package com.steve.authenticationService.configuration.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private final String secret;
    private final Long tokenLifespanMillis;

    public JwtProperties(String secret, Long tokenLifespanMillis) {
        this.secret = secret;
        this.tokenLifespanMillis = tokenLifespanMillis;
    }

    public String getSecret() {
        return secret;
    }

    public Long getTokenLifespanMillis() {
        return tokenLifespanMillis;
    }
}
