package com.sprint.BookInventoryMgmt.InventoryMgmt.exception;

public class DuplicateInventoryException extends RuntimeException {
    public DuplicateInventoryException(String message) {
        super(message);
    }
}