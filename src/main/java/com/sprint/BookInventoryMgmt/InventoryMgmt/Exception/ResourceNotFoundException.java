package com.sprint.BookInventoryMgmt.InventoryMgmt.Exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}