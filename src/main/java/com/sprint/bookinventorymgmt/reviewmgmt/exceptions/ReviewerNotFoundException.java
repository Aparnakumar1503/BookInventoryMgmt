package com.sprint.BookInventoryMgmt.reviewmgmt.exceptions;

public class ReviewerNotFoundException extends RuntimeException {

    public ReviewerNotFoundException(String message) {

        super(message);
    }
}