package com.sprint.bookinventorymgmt.reviewmgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRatingException extends RuntimeException {

    public InvalidRatingException(String message) {
        super(message);
    }

    public InvalidRatingException(Integer rating) {
        super("Rating must be between 1 and 10. Received: " + rating);
    }

    public InvalidRatingException(String message, Throwable cause) {
        super(message, cause);
    }
}
