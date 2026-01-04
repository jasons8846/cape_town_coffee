package com.jasons.coffeewiki.exceptions;

public class FieldRequiredException extends RuntimeException{

    public FieldRequiredException(String message){
        super(message);
    }
}
