package com.sprint.bookinventorymgmt.bookmgmt.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}