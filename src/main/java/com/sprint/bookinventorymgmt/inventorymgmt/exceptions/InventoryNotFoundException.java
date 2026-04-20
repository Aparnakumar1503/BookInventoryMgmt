package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(String message) {
        super(message);
    }
}