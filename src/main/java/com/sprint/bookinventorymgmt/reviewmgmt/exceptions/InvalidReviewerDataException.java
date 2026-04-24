package com.sprint.bookinventorymgmt.reviewmgmt.exceptions;

/**
 * Signals invalid reviewer input so reviewer-specific validation failures return a clear API error.
 */
public class InvalidReviewerDataException extends RuntimeException {

    public InvalidReviewerDataException(String message) {
        super(message);
    }
}
