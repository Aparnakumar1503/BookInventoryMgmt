package com.sprint.BookInventoryMgmt.InventoryMgmt.exception;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(String message) {
        super(message);
    }
}