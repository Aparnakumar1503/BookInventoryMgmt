package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInventoryDataException extends RuntimeException {
    public InvalidInventoryDataException(String message) {
        super(message);
    }

    public InvalidInventoryDataException(String fieldName, boolean invalidField) {
        super(fieldName + " cannot be null or empty");
    }

    public InvalidInventoryDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
