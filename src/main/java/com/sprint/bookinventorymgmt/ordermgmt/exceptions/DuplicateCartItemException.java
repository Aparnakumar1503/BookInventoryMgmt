package com.sprint.bookinventorymgmt.ordermgmt.exceptions;

public class DuplicateCartItemException extends RuntimeException {

    public DuplicateCartItemException(String message) {
        super(message);
    }
}
