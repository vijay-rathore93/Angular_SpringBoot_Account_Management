package org.accountmanagement.exception;


import org.accountmanagement.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handler(ConstraintViolationException exception) {
        Map<String, String> errorMessage=  exception.getConstraintViolations()
                .stream().collect(Collectors
                        .toMap(exceptionLocal->exceptionLocal.getPropertyPath().toString(),ConstraintViolation::getMessage));
        return new ResponseEntity<>(ApiResponse.builder().errors(errorMessage).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handler(HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(ApiResponse.builder().message(exception.getLocalizedMessage()).build(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handler(SQLIntegrityConstraintViolationException exception) {
        return new ResponseEntity<>(ApiResponse.builder().message(exception.getLocalizedMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handler(Exception exception) {
        return new ResponseEntity<>(ApiResponse.builder().message(exception.getLocalizedMessage()).build(), HttpStatus.BAD_REQUEST);
    }
}
