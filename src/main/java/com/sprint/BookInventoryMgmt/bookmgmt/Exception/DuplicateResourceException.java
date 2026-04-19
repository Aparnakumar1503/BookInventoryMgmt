package com.sprint.BookInventoryMgmt.BookMgmt.Exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}