package com.jasons.coffeewiki.controllers;

import com.jasons.coffeewiki.api.AuthenticationApi;
import com.jasons.coffeewiki.configs.PasswordConfig;
import com.jasons.coffeewiki.entities.Consumer;
import com.jasons.coffeewiki.exceptions.InvalidCredentials;
import com.jasons.coffeewiki.model.*;
import com.jasons.coffeewiki.services.CognitoService;
import com.jasons.coffeewiki.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthenticationController implements AuthenticationApi {

//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//
//    @Autowired
//    ConsumerRepository consumerRepository;
//
//    private final PasswordConfig passwordConfig;
//
//
//    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordConfig passwordConfig) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.passwordConfig = passwordConfig;
//    }
//
//    @Override
//    public ResponseEntity<AuthLoginResponseWrapper> authLogin(String xCorrelationId, AuthLoginRequestWrapper authLoginRequestWrapper) {
//
//        AuthLoginResponseWrapper wrapper = new AuthLoginResponseWrapper();
//        AuthResponse response = new AuthResponse();
//
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authLoginRequestWrapper.getData().getUsername(),
//                        authLoginRequestWrapper.getData().getPassword())
//        );
//
//        if(authentication.isAuthenticated()){
//            String token = jwtUtil.generateToken(authLoginRequestWrapper.getData().getUsername());
//            response.setBearer(token);
//            wrapper.setData(response);
//            wrapper.setError(null);
//
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(wrapper);
//        }else{
//            throw new InvalidCredentials("The username and/or password is incorrect");
//        }
//
//
//    }
//
//
    private final CognitoService cognitoService;

    public AuthenticationController(CognitoService cognitoService) {
        this.cognitoService = cognitoService;
    }

    @Override
    public ResponseEntity<RegisterResponseWrapper> register(String xCorrelationId, RegisterRequestWrapper registerRequestWrapper) {

        RegisterResponseWrapper wrapper = new RegisterResponseWrapper();
        RegisterResponse response = new RegisterResponse();

        cognitoService.registerUser(registerRequestWrapper.getData().getName(),
                registerRequestWrapper.getData().getUsername(),
                registerRequestWrapper.getData().getPassword(),
                registerRequestWrapper.getData().getEmail());


        response.setMessage("User registered and confirmation code sent to " + registerRequestWrapper.getData().getEmail());
        wrapper.setData(response);
        wrapper.setError(null);

        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }

    @Override
    public ResponseEntity<RegisterConfirmationResponseWrapper> registerConfirmation(String xCorrelationId, RegisterConfirmationRequestWrapper registerConfirmationRequestWrapper) {

        RegisterConfirmationResponseWrapper responseWrapper = new RegisterConfirmationResponseWrapper();
        RegisterConfirmationResponse response = new RegisterConfirmationResponse();

        response.setMessage(
                cognitoService.registerConfirmation(
                        registerConfirmationRequestWrapper.getData().getUsername(),
                        registerConfirmationRequestWrapper.getData().getConfirmationCode()
                )
        );
        responseWrapper.setData(response);
        responseWrapper.setError(null);


        return ResponseEntity.status(HttpStatus.OK)
                .body(responseWrapper);
    }

    @Override
    public ResponseEntity<AuthLoginResponseWrapper> authLogin(String xCorrelationId, AuthLoginRequestWrapper authLoginRequestWrapper) {
        AuthLoginResponseWrapper wrapper = new AuthLoginResponseWrapper();
        AuthResponse response = new AuthResponse();
        InitiateAuthResponse authResponse = cognitoService.logIn(
                authLoginRequestWrapper.getData().getUsername(),
                authLoginRequestWrapper.getData().getPassword());

        response.accessToken(authResponse.authenticationResult().accessToken());
        wrapper.setData(response);
        wrapper.setError(null);

        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);

    }

    @Override
    public ResponseEntity<ResetPasswordResponseWrapper> resetPassword(String xCorrelationId, ResetPasswordRequestWrapper resetPasswordRequestWrapper) {
        ResetPasswordResponseWrapper wrapper = new ResetPasswordResponseWrapper();
        ResetPasswordResponse response = new ResetPasswordResponse();

        response.setMessage(cognitoService.resetPassword(resetPasswordRequestWrapper.getData().getUsername()));
        wrapper.setData(response);
        wrapper.setError(null);

        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);
    }

    @Override
    public ResponseEntity<ResetPasswordConfirmationResponseWrapper> resetPasswordConfirmation(String xCorrelationId, ResetPasswordConfirmationRequestWrapper resetPasswordConfirmationRequestWrapper) {
        ResetPasswordConfirmationResponseWrapper wrapper = new ResetPasswordConfirmationResponseWrapper();
        ResetPasswordConfirmationResponse response = new ResetPasswordConfirmationResponse();

        response.setMessage(cognitoService.resetPasswordConfirmation(
                resetPasswordConfirmationRequestWrapper.getData().getUsername(),
                resetPasswordConfirmationRequestWrapper.getData().getNewPassword(),
                resetPasswordConfirmationRequestWrapper.getData().getConfirmationCode()));

        wrapper.setData(response);
        wrapper.setError(null);

        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);

    }

    @Override
    public ResponseEntity<DeleteAccountResponseWrapper> deleteAccount(String xCorrelationId, DeleteAccountRequestWrapper deleteAccountRequestWrapper) {
        DeleteAccountResponseWrapper wrapper = new DeleteAccountResponseWrapper();
        DeleteAccountResponse response = new DeleteAccountResponse();

        response.setMessage(cognitoService.deleteAccount(deleteAccountRequestWrapper.getData().getAccessToken()));

        wrapper.setData(response);
        wrapper.setError(null);

        return ResponseEntity.status(HttpStatus.OK)
                .body(wrapper);

    }
}
