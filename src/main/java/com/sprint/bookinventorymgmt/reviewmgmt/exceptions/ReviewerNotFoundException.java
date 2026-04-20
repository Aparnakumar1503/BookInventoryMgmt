package com.sprint.bookinventorymgmt.reviewmgmt.exceptions;

public class ReviewerNotFoundException extends RuntimeException {

    public ReviewerNotFoundException(String message) {

        super(message);
    }
}