package com.jasons.coffeewiki.exceptions;

public class InvalidCredentials extends RuntimeException{

    public InvalidCredentials(String message){
        super(message);
    }
}
