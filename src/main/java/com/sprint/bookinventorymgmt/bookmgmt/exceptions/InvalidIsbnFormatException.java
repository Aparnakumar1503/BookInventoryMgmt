package com.sprint.bookinventorymgmt.bookmgmt.exceptions;

public class InvalidIsbnFormatException extends RuntimeException {

    public InvalidIsbnFormatException(String message) {
        super(message);
    }
}
