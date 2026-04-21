package com.sprint.bookinventorymgmt.bookmgmt.exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}