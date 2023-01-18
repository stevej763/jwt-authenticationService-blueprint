package com.steve.authenticationService.domain;

import com.steve.authenticationService.client.AccountRegistrationRequest;
import com.steve.authenticationService.client.AuthenticationError;
import com.steve.authenticationService.client.AuthenticationRequest;
import com.steve.authenticationService.client.AuthenticationResponse;
import com.steve.authenticationService.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder passwordEncoder,
                                 UserRepository userRepository,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(AccountRegistrationRequest accountRegistrationRequest) {
        String accountCreationRequestEmail = accountRegistrationRequest.email();
        Optional<User> existingEmailCheck = userRepository.findByEmail(accountCreationRequestEmail);
        if (existingEmailCheck.isEmpty()) {
            User user = createUser(accountRegistrationRequest);
            userRepository.saveUser(user);
            return generateAuthenticationResponse(user);
        }
        AuthenticationError error = new AuthenticationError("A user with that email already exists");
        return new AuthenticationResponse(false, error);

    }

    private User createUser(AccountRegistrationRequest accountRegistrationRequest) {
        String encodedPassword = passwordEncoder.encode(accountRegistrationRequest.password());
        return new User(
                UUID.randomUUID(),
                accountRegistrationRequest.firstName(),
                accountRegistrationRequest.lastName(),
                accountRegistrationRequest.email(),
                encodedPassword,
                Role.USER);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.email(), authenticationRequest.password());
        try {
            Authentication authenticate = authenticationManager.authenticate(authentication);
            LOGGER.info("authentication details={}", authenticate);
            User user = userRepository.findByEmail(authenticationRequest.email()).orElseThrow();
            return generateAuthenticationResponse(user);
        } catch (AuthenticationException exception) {
            AuthenticationError authenticationError = new AuthenticationError(exception.getMessage());
            return new AuthenticationResponse( false, authenticationError);
        }
    }

    private AuthenticationResponse generateAuthenticationResponse(User user) {
        String jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt, true);
    }
}
