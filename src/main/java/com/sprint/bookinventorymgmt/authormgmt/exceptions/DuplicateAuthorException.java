package com.sprint.bookinventorymgmt.authormgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateAuthorException extends RuntimeException {

    public DuplicateAuthorException(String message) {
        super(message);
    }

    public DuplicateAuthorException(String firstName, String lastName) {
        super("Author already exists with name: " + firstName + " " + lastName);
    }

    public DuplicateAuthorException(String message, Throwable cause) {
        super(message, cause);
    }
}
