package com.sprint.bookinventorymgmt.bookmgmt.exceptions;

public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(String message) {
        super(message);
    }
}