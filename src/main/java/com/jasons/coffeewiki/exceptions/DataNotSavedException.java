package com.jasons.coffeewiki.exceptions;

public class DataNotSavedException extends RuntimeException{
    public DataNotSavedException(String message){
        super(message);
    }
}
