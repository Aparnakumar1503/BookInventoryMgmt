package com.sprint.BookInventoryMgmt.bookmgmt.exceptions;

public class PublisherNotFoundException extends RuntimeException {

    public PublisherNotFoundException(String message) {
        super(message);
    }
}