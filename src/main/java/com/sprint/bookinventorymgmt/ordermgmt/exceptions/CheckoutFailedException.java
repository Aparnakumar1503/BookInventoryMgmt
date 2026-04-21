package com.sprint.bookinventorymgmt.ordermgmt.exceptions;

public class CheckoutFailedException extends RuntimeException {

    public CheckoutFailedException(String message) {
        super(message);
    }
}
