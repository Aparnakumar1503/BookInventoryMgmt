package com.sprint.bookinventorymgmt.ordermgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyCartException extends RuntimeException {

    public EmptyCartException(String message) {
        super(message);
    }

    public EmptyCartException(Integer userId) {
        super("Cart is empty for userId: " + userId);
    }

    public EmptyCartException(String message, Throwable cause) {
        super(message, cause);
    }
}
