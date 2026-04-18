package com.sprint.BookInventoryMgmt.authorMgmt.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}