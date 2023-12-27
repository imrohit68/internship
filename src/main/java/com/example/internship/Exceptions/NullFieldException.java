package com.example.internship.Exceptions;

public class NullFieldException extends RuntimeException{
    private String message;

    public NullFieldException(String message) {
        super(String.format(message));
        this.message = message;
    }
}
