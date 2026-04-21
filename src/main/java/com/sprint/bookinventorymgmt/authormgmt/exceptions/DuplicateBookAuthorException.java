package com.sprint.bookinventorymgmt.authormgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateBookAuthorException extends RuntimeException {

    public DuplicateBookAuthorException(String message) {
        super(message);
    }

    public DuplicateBookAuthorException(String isbn, Integer authorId) {
        super("Book author mapping already exists for isbn: " + isbn + " and authorId: " + authorId);
    }

    public DuplicateBookAuthorException(String message, Throwable cause) {
        super(message, cause);
    }
}
