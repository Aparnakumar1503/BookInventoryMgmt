package com.sprint.BookInventoryMgmt.reviewmgmt.exception;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(String message)
    {
        super(message);
    }
}