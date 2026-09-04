package com.jasons.coffeewiki.controllers;

import com.jasons.coffeewiki.api.AuthenticationApi;
import com.jasons.coffeewiki.configs.PasswordConfig;
import com.jasons.coffeewiki.entities.Consumer;
import com.jasons.coffeewiki.exceptions.InvalidCredentials;
import com.jasons.coffeewiki.model.*;
import com.jasons.coffeewiki.repositories.ConsumerRepository;
import com.jasons.coffeewiki.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    ConsumerRepository consumerRepository;

    private final PasswordConfig passwordConfig;


    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordConfig passwordConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordConfig = passwordConfig;
    }

    @Override
    public ResponseEntity<AuthLoginResponseWrapper> authLogin(String xCorrelationId, AuthLoginRequestWrapper authLoginRequestWrapper) {

        AuthLoginResponseWrapper wrapper = new AuthLoginResponseWrapper();
        AuthResponse response = new AuthResponse();


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authLoginRequestWrapper.getData().getUsername(),
                        authLoginRequestWrapper.getData().getPassword())
        );

        if(authentication.isAuthenticated()){
            String token = jwtUtil.generateToken(authLoginRequestWrapper.getData().getUsername());
            response.setBearer(token);
            wrapper.setData(response);
            wrapper.setError(null);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(wrapper);
        }else{
            throw new InvalidCredentials("The username and/or password is incorrect");
        }


    }


    @Override
    public ResponseEntity<RegisterResponseWrapper> register(String xCorrelationId, RegisterRequestWrapper registerRequestWrapper) {

        System.out.println("Start reg process");
        RegisterResponseWrapper wrapper = new RegisterResponseWrapper();
        RegisterResponse response = new RegisterResponse();
        Consumer consumer = new Consumer();


        System.out.println("Assign object");
        consumer.setName(registerRequestWrapper.getData().getName());
        consumer.setUsername(registerRequestWrapper.getData().getUsername());
        consumer.setPassword(passwordConfig.encoder().encode(registerRequestWrapper.getData().getPassword()));
        consumer.setRoles(registerRequestWrapper.getData().getRoles());

        System.out.println(consumer.getUsername());

        consumerRepository.save(consumer);

        response.setMessage("User registered");
        wrapper.setData(response);
        wrapper.setError(null);

        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }
}
