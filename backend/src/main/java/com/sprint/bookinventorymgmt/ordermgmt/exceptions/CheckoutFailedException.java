package com.sprint.bookinventorymgmt.ordermgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CheckoutFailedException extends RuntimeException {

    public CheckoutFailedException(String message) {
        super(message);
    }

    public CheckoutFailedException(Integer inventoryId) {
        super("Checkout failed for inventoryId: " + inventoryId);
    }

    public CheckoutFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
