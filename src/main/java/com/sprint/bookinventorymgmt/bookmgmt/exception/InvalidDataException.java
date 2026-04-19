package com.sprint.bookinventorymgmt.bookmgmt.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}