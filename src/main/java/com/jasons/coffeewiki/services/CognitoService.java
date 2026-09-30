package com.jasons.coffeewiki.services;

import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

import java.util.Map;

public interface CognitoService {

    public String registerUser(String name, String username, String password, String email);
    public String registerConfirmation(String username, String confirmationCode);
    public InitiateAuthResponse logIn(String username, String password);
    public String resetPassword(String username);
    public String resetPasswordConfirmation(String username, String password, String confirmationCode);
    public String deleteAccount(String accessToken);
}
