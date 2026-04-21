package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

public class InsufficientInventoryException extends RuntimeException {

    public InsufficientInventoryException(String message) {
        super(message);
    }
}
