package com.sprint.bookinventorymgmt.ordermgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCartItemException extends RuntimeException {

    public DuplicateCartItemException(String message) {
        super(message);
    }

    public DuplicateCartItemException(Integer userId, String isbn) {
        super("Cart item already exists for userId: " + userId + " isbn: " + isbn);
    }

    public DuplicateCartItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
