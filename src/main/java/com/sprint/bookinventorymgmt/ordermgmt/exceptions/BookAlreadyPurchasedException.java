package com.sprint.bookinventorymgmt.ordermgmt.exceptions;

public class BookAlreadyPurchasedException extends RuntimeException {

    public BookAlreadyPurchasedException(String message) {
        super(message);
    }
}
