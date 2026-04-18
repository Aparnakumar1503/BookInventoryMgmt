package com.sprint.BookInventoryMgmt.BookMgmt.Exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}