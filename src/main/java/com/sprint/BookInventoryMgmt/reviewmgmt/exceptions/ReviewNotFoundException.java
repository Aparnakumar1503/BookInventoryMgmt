package com.sprint.BookInventoryMgmt.reviewmgmt.exceptions;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(String message)
    {
        super(message);
    }
}