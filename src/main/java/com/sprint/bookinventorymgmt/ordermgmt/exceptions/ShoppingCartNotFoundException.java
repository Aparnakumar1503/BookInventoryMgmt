package com.sprint.bookinventorymgmt.ordermgmt.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShoppingCartNotFoundException extends RuntimeException {

    public ShoppingCartNotFoundException(String message) {
        super(message);
    }

    public ShoppingCartNotFoundException(Integer userId, String isbn) {
        super("Cart not found for userId: " + userId + " isbn: " + isbn);
    }

    public ShoppingCartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
