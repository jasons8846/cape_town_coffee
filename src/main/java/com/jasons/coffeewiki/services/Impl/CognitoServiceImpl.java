package com.jasons.coffeewiki.services.Impl;

import com.jasons.coffeewiki.services.CognitoService;
import com.jasons.coffeewiki.exceptions.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CognitoServiceImpl implements CognitoService {

    private final CognitoIdentityProviderClient cognitoClient;
    @Value("${aws.app.client.id}")
    private String clientId;

    @Value("${aws.user.pool.id}")
    private String userPoolId;

    @Value("${aws.user.group}")
    private String userGroup;

    public CognitoServiceImpl(CognitoIdentityProviderClient cognitoClient) {
        this.cognitoClient = cognitoClient;
    }


    @Override
    public String registerUser(String name, String username, String password, String email) {
        try{
            List<AttributeType> userAttributes = new ArrayList<>();
            userAttributes.add(AttributeType.builder().name("email").value(email).build());
            userAttributes.add(AttributeType.builder().name("name").value(name).build());


            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .clientId(clientId)
                    .username(username)
                    .password(password)
                    .userAttributes(userAttributes)
                    .build();

            SignUpResponse response = cognitoClient.signUp(signUpRequest);

            AdminAddUserToGroupRequest groupRequest = AdminAddUserToGroupRequest.builder()
                    .userPoolId(userPoolId)
                    .username(username)
                    .groupName(userGroup)
                    .build();

            cognitoClient.adminAddUserToGroup(groupRequest);

            return "User registered successfully. Confirmed status: " + response.userConfirmed();
        }catch (CognitoIdentityProviderException e){

            String message = e.awsErrorDetails() != null ? e.awsErrorDetails().errorMessage() : "Unable to register user";
            throw new AuthenticationException( "Registration failed: " + message);
        }
    }

    @Override
    public String registerConfirmation(String username, String confirmationCode) {

        try {
            ConfirmSignUpRequest confirmSignUpRequest = ConfirmSignUpRequest.builder()
                    .clientId(clientId)
                    .username(username)
                    .confirmationCode(confirmationCode)
                    .build();

            cognitoClient.confirmSignUp(confirmSignUpRequest);

            return "Registration confirmed";
        }catch (CognitoIdentityProviderException e){
            String message = e.awsErrorDetails() != null ? e.awsErrorDetails().errorMessage() : "Unable to confirm user";
            throw new AuthenticationException( "Registration confirmation failed: " + message);
        }
    }

    @Override
    public InitiateAuthResponse logIn(String username, String password) {

        Map<String, String> authParams = new HashMap<>();
        Map<String, String> result = new HashMap<>();
        authParams.put("USERNAME", username);
        authParams.put("PASSWORD", password);

        InitiateAuthRequest initiateAuthRequest = InitiateAuthRequest.builder()
                .clientId(clientId)
                .authFlow("USER_PASSWORD_AUTH")
                .authParameters(authParams)
                .build();

            try {

                return cognitoClient.initiateAuth(initiateAuthRequest);
            }catch (CognitoIdentityProviderException e){

                String message = e.awsErrorDetails() != null ? e.awsErrorDetails().errorMessage() : "Unable to authenticate user";
                throw new AuthenticationException( "Login failed: " + message);

            }


    }

    @Override
    public String resetPassword(String username) {
        try {

            ForgotPasswordRequest initiateAuthRequest = ForgotPasswordRequest.builder()
                    .clientId(clientId)
                    .username(username)
                    .build();

            cognitoClient.forgotPassword(initiateAuthRequest);
            return "Password reset confirmation code sent";

        }catch (CognitoIdentityProviderException e){
            String message = e.awsErrorDetails() != null ? e.awsErrorDetails().errorMessage() : "Unable to reset password";
            throw new AuthenticationException( "Password reset failed: " + message);
        }
    }

    @Override
    public String resetPasswordConfirmation(String username, String password, String confirmationCode) {
        try {


            ConfirmForgotPasswordRequest confirmForgotPasswordRequest = ConfirmForgotPasswordRequest.builder()
                    .clientId(clientId)
                    .username(username)
                    .confirmationCode(confirmationCode)
                    .password(password)
                    .build();

            cognitoClient.confirmForgotPassword(confirmForgotPasswordRequest);

            return "Password Reset Success.";

        }catch (CognitoIdentityProviderException e){
            String message = e.awsErrorDetails() != null ? e.awsErrorDetails().errorMessage() : "Unable to confirm password reset";
            throw new AuthenticationException( "Password reset confirmation failed: " + message);
        }
    }

    @Override
    public String deleteAccount(String accessToken) {
        try {

            DeleteUserRequest deleteUserRequest = DeleteUserRequest.builder()
                    .accessToken(accessToken)
                    .build();

            cognitoClient.deleteUser(deleteUserRequest);

            return "User deleted.";

        }catch (CognitoIdentityProviderException e){
            String message = e.awsErrorDetails() != null ? e.awsErrorDetails().errorMessage() : "Unable to delete user";
            throw new AuthenticationException( "User deletion failed: " + message);
        }
    }
}
