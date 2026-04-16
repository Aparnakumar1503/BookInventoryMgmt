package com.sprint.BookInventoryMgmt.BookMgmt.Exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message) {
        super(message);
    }
}