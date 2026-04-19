package com.sprint.BookInventoryMgmt.inventorymgmt.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}