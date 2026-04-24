package com.sprint.bookinventorymgmt.ordermgmt.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PurchaseNotFoundException extends RuntimeException {

    public PurchaseNotFoundException(String message) {
        super(message);
    }

    public PurchaseNotFoundException(Integer userId, Integer inventoryId) {
        super("Purchase not found for userId: " + userId + " inventoryId: " + inventoryId);
    }

    public PurchaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
