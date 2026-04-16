package com.sprint.BookInventoryMgmt.AuthorMgmt.Exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}