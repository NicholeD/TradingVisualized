package com.kenzie.capstone.service.exceptions;

public class ResponseStatusException extends RuntimeException{

    public ResponseStatusException(String message){
        super(message);
    }
}
