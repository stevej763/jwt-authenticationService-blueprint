package com.steve.authenticationService.configuration;

import com.steve.authenticationService.configuration.jwt.JwtProperties;
import com.steve.authenticationService.domain.AuthenticationService;
import com.steve.authenticationService.domain.JwtService;
import com.steve.authenticationService.domain.JwtValidator;
import com.steve.authenticationService.domain.UserDetailsService;
import com.steve.authenticationService.repository.UserRepository;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ConfigurationPropertiesScan("com.steve.authenticationService.configuration")
public class AuthenticationServiceConfiguration {

    private final JwtProperties jwtProperties;
    private final MongoTemplate userDao;
    private final AuthenticationConfiguration authenticationConfiguration;

    public AuthenticationServiceConfiguration(JwtProperties jwtProperties, MongoTemplate userDao, AuthenticationConfiguration authenticationConfiguration) {
        this.jwtProperties = jwtProperties;
        this.userDao = userDao;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public JwtService jwtService() {
        return new JwtService(jwtProperties);
    }

    @Bean
    public JwtValidator jwtValidator() {
        return new JwtValidator(jwtService(), userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository(userDao);
    }

    @Bean
    public AuthenticationService authenticationService() throws Exception {
        return new AuthenticationService(passwordEncoder(), userRepository(), jwtService(), authenticationManager(authenticationConfiguration));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}
