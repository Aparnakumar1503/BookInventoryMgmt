package com.sprint.bookinventorymgmt.ordermgmt.exceptions;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException(String message) {
        super(message);
    }
}
