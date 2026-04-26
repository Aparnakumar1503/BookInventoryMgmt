package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InsufficientInventoryException extends RuntimeException {

    public InsufficientInventoryException(String message) {
        super(message);
    }

    public InsufficientInventoryException(String isbn, Integer requiredQuantity) {
        super("Insufficient inventory for isbn: " + isbn + ", required quantity: " + requiredQuantity);
    }

    public InsufficientInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
