package com.example.test.exceptions;

public class EmailNotCorrectException extends RuntimeException{
    public EmailNotCorrectException(String msg){
        super(msg);
    }
}
