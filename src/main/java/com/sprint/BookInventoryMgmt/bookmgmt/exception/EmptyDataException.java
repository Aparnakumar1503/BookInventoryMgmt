package com.sprint.bookinventorymgmt.bookmgmt.exception;

public class EmptyDataException extends RuntimeException {
    public EmptyDataException(String message) {
        super(message);
    }
}