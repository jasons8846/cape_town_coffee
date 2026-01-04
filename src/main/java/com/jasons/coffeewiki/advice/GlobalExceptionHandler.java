package com.jasons.coffeewiki.advice;

import com.jasons.coffeewiki.exceptions.*;
import com.jasons.coffeewiki.model.ErrorResponse;
import com.jasons.coffeewiki.model.ErrorResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.InvalidUrlException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseWrapper> fieldRequired(MethodArgumentNotValidException ex){
        ErrorResponseWrapper wrapper = new ErrorResponseWrapper();
        ErrorResponse response = new ErrorResponse();

        response.setMessage("Field required");

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            response.setDetails(error.getField() + " is required");
        });

        wrapper.setData(null);
        wrapper.setError(response);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(wrapper);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseWrapper> fieldRequired(HttpMessageNotReadableException ex){
        ErrorResponseWrapper wrapper = new ErrorResponseWrapper();
        ErrorResponse response = new ErrorResponse();

        response.setMessage("Field required");
        response.setDetails(ex.getMessage());

        wrapper.setData(null);
        wrapper.setError(response);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(wrapper);
    }


    @ExceptionHandler(FieldRequiredException.class)
    public ResponseEntity<ErrorResponseWrapper> fieldRequired(FieldRequiredException ex){
        ErrorResponseWrapper wrapper = new ErrorResponseWrapper();
        ErrorResponse response = new ErrorResponse();

        response.setMessage("Field required");
        response.setDetails(ex.getMessage());

        wrapper.setData(null);
        wrapper.setError(response);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(wrapper);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseWrapper> notFoundException(NotFoundException ex){
        ErrorResponseWrapper wrapper = new ErrorResponseWrapper();
        ErrorResponse response = new ErrorResponse();

        response.setMessage("No data found");
        response.setDetails(ex.getMessage());

        wrapper.setData(null);
        wrapper.setError(response);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(wrapper);
    }

    @ExceptionHandler(DataNotSavedException.class)
    public ResponseEntity<ErrorResponseWrapper> dataNotSaved(DataNotSavedException ex){
        ErrorResponseWrapper wrapper = new ErrorResponseWrapper();
        ErrorResponse response = new ErrorResponse();

        response.setMessage("Data not saved");
        response.setDetails(ex.getMessage());

        wrapper.setData(null);
        wrapper.setError(response);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(wrapper);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseWrapper> usernameNotFound(UsernameNotFoundException ex){
        ErrorResponseWrapper wrapper = new ErrorResponseWrapper();
        ErrorResponse response = new ErrorResponse();

        response.setMessage("Username not found");
        response.setDetails(ex.getMessage());

        wrapper.setData(null);
        wrapper.setError(response);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(wrapper);
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ErrorResponseWrapper> invalidCredentials(InvalidCredentials ex){
        ErrorResponseWrapper wrapper = new ErrorResponseWrapper();
        ErrorResponse response = new ErrorResponse();

        response.setMessage("Invalid credentials");
        response.setDetails(ex.getMessage());

        wrapper.setData(null);
        wrapper.setError(response);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(wrapper);
    }


}
