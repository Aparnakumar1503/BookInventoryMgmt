package com.sprint.bookinventorymgmt.bookmgmt.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}