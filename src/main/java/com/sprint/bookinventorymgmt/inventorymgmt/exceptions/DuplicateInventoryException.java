package com.sprint.BookInventoryMgmt.inventorymgmt.exceptions;

public class DuplicateInventoryException extends RuntimeException {
    public DuplicateInventoryException(String message) {
        super(message);
    }
}