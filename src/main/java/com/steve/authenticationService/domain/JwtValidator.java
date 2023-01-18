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

    public ValidationResponse validateJwt(String accessKey) throws AuthenticationException {
        if (accessKey == null || accessKey.equals("")) {
            LOGGER.info("access token not provided");
            return new ValidationResponse(false, "access token not provided");
        }
        try {
            String email = jwtService.extractEmail(accessKey);
            LOGGER.info("extracted email={}", email);
            if (email == null) {
                String message = "No email provided in token";
                return new ValidationResponse(false, message);
            }
            UserDetails userDetails = getUserDetailsOrThrow(email);
            boolean result = authenticateValidJwt(accessKey, userDetails);
            return new ValidationResponse(result);
        } catch (Exception exception) {
            LOGGER.info("Error decoding jwt");
            return new ValidationResponse(false, exception.getMessage());
        }
    }

    private boolean authenticateValidJwt(String jwt, UserDetails userDetails) {
        if (!jwtService.isTokenValid(jwt, userDetails)) {
            LOGGER.info("accessKey not valid for user={}", userDetails);
            return false;
        }
        LOGGER.info("accessKey is valid for user={}", userDetails);
        return true;
    }

    private UserDetails getUserDetailsOrThrow(String email) {
        return userDetailsService.loadUserByUsername(email);
    }
}