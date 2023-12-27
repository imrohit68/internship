package com.example.internship.Exceptions;

public class EmployeeAlreadyExists extends  RuntimeException{
    String message;

    public EmployeeAlreadyExists(String message) {
        super(String.format(message));
        this.message = message;
    }
}
