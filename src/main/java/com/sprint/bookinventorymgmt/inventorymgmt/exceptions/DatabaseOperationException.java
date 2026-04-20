package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message) {
        super(message);
    }
}