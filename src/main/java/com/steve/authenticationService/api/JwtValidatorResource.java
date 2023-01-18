package com.steve.authenticationService.api;

import com.steve.authenticationService.client.JwtValidationRequest;
import com.steve.authenticationService.client.JwtValidationResponse;
import com.steve.authenticationService.domain.JwtValidator;
import com.steve.authenticationService.domain.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/api/v1/auth")
public class JwtValidatorResource {

    private final JwtValidator jwtValidator;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtValidatorResource.class);

    public JwtValidatorResource(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtValidationResponse> validate(@RequestBody JwtValidationRequest jwtValidationRequest) {
        try {
            LOGGER.info("validating accessKey={}", jwtValidationRequest.accessKey());
            ValidationResponse response = jwtValidator.validateJwt(jwtValidationRequest.accessKey());
            LOGGER.info("response={}", response);
            return ResponseEntity.ok(new JwtValidationResponse(response.valid(), response.error()));
        } catch (AuthenticationException exception) {
            LOGGER.info("exception caught={}", exception.getMessage());
            return ResponseEntity.ok(new JwtValidationResponse(false, "authentication error"));
        }
    }
}
