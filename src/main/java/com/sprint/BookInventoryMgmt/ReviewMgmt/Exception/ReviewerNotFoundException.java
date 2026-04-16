package com.sprint.BookInventoryMgmt.ReviewMgmt.Exception;

public class ReviewerNotFoundException extends RuntimeException {

    public ReviewerNotFoundException(String message) {
        super(message);
    }
}