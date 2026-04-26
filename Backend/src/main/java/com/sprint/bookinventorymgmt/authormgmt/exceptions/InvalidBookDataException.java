package com.sprint.bookinventorymgmt.authormgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBookDataException extends RuntimeException {

    public InvalidBookDataException(String message) {
        super(message);
    }

    public InvalidBookDataException(String fieldName, boolean invalidField) {
        super(fieldName + " cannot be null or empty");
    }

    public InvalidBookDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
