package com.sprint.bookinventorymgmt.reviewmgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(String message)
    {
        super(message);
    }

    public ReviewNotFoundException(Integer reviewId) {
        super("Review not found with ID: " + reviewId);
    }

    public ReviewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
