package org.accountmanagement.exception;

public class AccountException extends RuntimeException{

    private String message;

    public AccountException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
