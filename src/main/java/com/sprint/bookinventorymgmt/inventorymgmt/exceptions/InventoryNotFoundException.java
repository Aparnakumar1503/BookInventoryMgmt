package com.sprint.BookInventoryMgmt.inventorymgmt.exceptions;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(String message) {
        super(message);
    }
}