package com.sprint.BookInventoryMgmt.bookmgmt.exceptions;

public class StateNotFoundException extends RuntimeException {

    public StateNotFoundException(String message) {
        super(message);
    }
}