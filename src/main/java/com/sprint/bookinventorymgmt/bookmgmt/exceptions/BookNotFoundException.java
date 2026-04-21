package com.sprint.bookinventorymgmt.bookmgmt.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message) {
        super(message);
    }
}