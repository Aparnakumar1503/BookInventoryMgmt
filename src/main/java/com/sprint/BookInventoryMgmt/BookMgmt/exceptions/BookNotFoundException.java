package com.sprint.BookInventoryMgmt.bookmgmt.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message) {
        super(message);
    }
}