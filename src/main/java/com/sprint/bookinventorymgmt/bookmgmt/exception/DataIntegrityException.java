package com.sprint.bookinventorymgmt.bookmgmt.exception;

public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(String message) {
        super(message);
    }
}