package com.sprint.bookinventorymgmt.bookmgmt.exceptions;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}