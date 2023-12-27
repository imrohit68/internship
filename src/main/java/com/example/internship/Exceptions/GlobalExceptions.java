package com.example.internship.Exceptions;

import com.example.internship.Controller.EmployeeController;
import com.example.internship.Responses.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptions {
    @ExceptionHandler(EmployeeDoesNotExist.class)
    public ResponseEntity<StatusResponse> employeeDoesNotExist(EmployeeDoesNotExist e){
        StatusResponse statusResponse = new StatusResponse(e.getMessage(),false);
        return new ResponseEntity<>(statusResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<StatusResponse> nullFieldException(NullFieldException e){
        return new ResponseEntity<>(new StatusResponse(e.getMessage(),false),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(EmployeeAlreadyExists.class)
    public ResponseEntity<StatusResponse> EmployeeAlreadyExists(EmployeeAlreadyExists e){
        return new ResponseEntity<>(new StatusResponse(e.getMessage(),false),HttpStatus.BAD_REQUEST);
    }
}
