package com.sprint.BookInventoryMgmt.UserMgmt.Exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
