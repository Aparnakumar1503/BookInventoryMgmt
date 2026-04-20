package com.sprint.bookinventorymgmt.reviewmgmt.exceptions;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(String message)
    {
        super(message);
    }
}