package com.sprint.BookInventoryMgmt.InventoryMgmt.exception;

public class InvalidInventoryDataException extends RuntimeException {
    public InvalidInventoryDataException(String message) {
        super(message);
    }
}