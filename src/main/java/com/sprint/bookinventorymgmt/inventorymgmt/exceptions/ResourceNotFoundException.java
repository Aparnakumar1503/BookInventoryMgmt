package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Integer resourceId) {
        super(resourceName + " not found with id: " + resourceId);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
