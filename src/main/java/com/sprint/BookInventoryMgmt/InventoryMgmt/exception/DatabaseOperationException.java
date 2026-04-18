package com.sprint.BookInventoryMgmt.InventoryMgmt.exception;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message) {
        super(message);
    }
}