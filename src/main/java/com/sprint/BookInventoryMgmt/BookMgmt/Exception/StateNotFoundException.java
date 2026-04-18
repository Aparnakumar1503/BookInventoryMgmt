package com.sprint.BookInventoryMgmt.BookMgmt.Exception;

public class StateNotFoundException extends RuntimeException {

    public StateNotFoundException(String message) {
        super(message);
    }
}