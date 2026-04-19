package com.sprint.bookinventorymgmt.bookmgmt.exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message) {
        super(message);
    }
}