package com.sprint.bookinventorymgmt.ordermgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyPurchasedException extends RuntimeException {

    public BookAlreadyPurchasedException(String message) {
        super(message);
    }

    public BookAlreadyPurchasedException(Integer inventoryId) {
        super("Book with inventory ID " + inventoryId + " has already been purchased.");
    }

    public BookAlreadyPurchasedException(Integer inventoryId, Integer userId) {
        super("User ID " + userId + " has already purchased book with inventory ID " + inventoryId + ".");
    }

    public BookAlreadyPurchasedException(String message, Throwable cause) {
        super(message, cause);
    }
}
