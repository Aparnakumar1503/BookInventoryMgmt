package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}