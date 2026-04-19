package com.sprint.BookInventoryMgmt.inventorymgmt.exceptions;

public class BookConditionNotFoundException extends RuntimeException {

    public BookConditionNotFoundException(String message) {
        super(message);
    }
}