package com.example.internship.Exceptions;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

public class EmployeeDoesNotExist extends RuntimeException{
    private String message;

    public EmployeeDoesNotExist(String message) {
        super(String.format(message));
        this.message = message;
    }
}
