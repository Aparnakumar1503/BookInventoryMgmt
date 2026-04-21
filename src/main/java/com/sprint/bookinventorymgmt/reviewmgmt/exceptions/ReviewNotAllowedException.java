package com.sprint.bookinventorymgmt.reviewmgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ReviewNotAllowedException extends RuntimeException {

    public ReviewNotAllowedException(String message) {
        super(message);
    }

    public ReviewNotAllowedException(Integer reviewerId, String isbn) {
        super("Reviewer ID " + reviewerId + " is not allowed to review isbn: " + isbn);
    }

    public ReviewNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
