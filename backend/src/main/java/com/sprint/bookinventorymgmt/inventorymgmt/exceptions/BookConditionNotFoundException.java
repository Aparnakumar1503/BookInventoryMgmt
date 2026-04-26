package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookConditionNotFoundException extends RuntimeException {

    public BookConditionNotFoundException(String message) {
        super(message);
    }

    public BookConditionNotFoundException(Integer rank) {
        super("Book condition not found with rank: " + rank);
    }

    public BookConditionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
