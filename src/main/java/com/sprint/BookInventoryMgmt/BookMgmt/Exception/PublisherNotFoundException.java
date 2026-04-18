package com.sprint.BookInventoryMgmt.BookMgmt.Exception;

public class PublisherNotFoundException extends RuntimeException {

    public PublisherNotFoundException(String message) {
        super(message);
    }
}