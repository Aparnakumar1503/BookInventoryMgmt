package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateInventoryException extends RuntimeException {
    public DuplicateInventoryException(String message) {
        super(message);
    }

    public DuplicateInventoryException(String isbn, Integer rank) {
        super("Inventory already exists for isbn: " + isbn + " and rank: " + rank);
    }

    public DuplicateInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
