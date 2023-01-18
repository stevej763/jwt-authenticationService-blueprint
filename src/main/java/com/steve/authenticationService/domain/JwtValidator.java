package com.steve.authenticationService.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.sasl.AuthenticationException;

public class JwtValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtValidator.class);
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtValidator(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public boolean validateJwt(String authHeader) throws AuthenticationException {

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            LOGGER.info("fell over at the first check for Bearer and not null");
            return false;
        }
        String jwt = authHeader.substring(7);
        LOGGER.info("extracted jwt={}", jwt);
        String email = jwtService.extractEmail(jwt);
        LOGGER.info("extracted email={}", email);
        if (email == null) {
            LOGGER.info("email not found or something like that");
            throw new AuthenticationException("email not found or something like that");
        }
        UserDetails userDetails = getUserDetailsOrThrow(email);
        boolean result = authenticateValidJwt(jwt, userDetails);
        LOGGER.info("should be returning true");
        return result;
    }

    private boolean authenticateValidJwt(String jwt, UserDetails userDetails) {
        LOGGER.info("authenticating user={} with granted authorities={}", userDetails.getUsername(), userDetails.getAuthorities());
        if (jwtService.isTokenValid(jwt, userDetails)) {
            LOGGER.info("Token is valid");
            return true;
        } else {
            LOGGER.info("token not valid for user={}", userDetails);
            return false;
        }

    }

    private UserDetails getUserDetailsOrThrow(String email) {
        return userDetailsService.loadUserByUsername(email);
    }
}