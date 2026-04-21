package com.sprint.bookinventorymgmt.bookmgmt.exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}