package com.sprint.BookInventoryMgmt.ordermgmt.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyCartException extends RuntimeException {

    public EmptyCartException() {
        super("Cannot proceed. Shopping cart is empty.");
    }

    public EmptyCartException(String message) {
        super(message);
    }

    public EmptyCartException(Long cartId) {
        super("Shopping cart with ID " + cartId + " is empty. Add items before proceeding.");
    }

    public EmptyCartException(String message, Throwable cause) {
        super(message, cause);
    }
}