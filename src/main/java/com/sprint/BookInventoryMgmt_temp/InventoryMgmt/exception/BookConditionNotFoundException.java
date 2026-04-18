package com.sprint.BookInventoryMgmt.InventoryMgmt.exception;

public class BookConditionNotFoundException extends RuntimeException {

    public BookConditionNotFoundException(String message) {
        super(message);
    }
}