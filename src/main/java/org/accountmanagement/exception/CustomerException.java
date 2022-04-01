package org.accountmanagement.exception;

public class CustomerException extends RuntimeException{

    private String message;

    public CustomerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}