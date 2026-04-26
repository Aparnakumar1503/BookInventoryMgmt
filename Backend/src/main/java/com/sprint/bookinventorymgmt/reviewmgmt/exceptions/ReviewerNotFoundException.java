package com.sprint.bookinventorymgmt.reviewmgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewerNotFoundException extends RuntimeException {

    public ReviewerNotFoundException(String message) {

        super(message);
    }

    public ReviewerNotFoundException(Integer reviewerId) {
        super("Reviewer not found with ID: " + reviewerId);
    }

    public ReviewerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
