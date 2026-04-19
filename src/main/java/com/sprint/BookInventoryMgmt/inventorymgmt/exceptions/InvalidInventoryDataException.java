package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

public class InvalidInventoryDataException extends RuntimeException {
    public InvalidInventoryDataException(String message) {
        super(message);
    }
}