package com.sprint.bookinventorymgmt.bookmgmt.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}