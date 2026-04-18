package com.sprint.BookInventoryMgmt.ordermgmt.exception;


public class PurchaseNotFoundException extends RuntimeException {

    public PurchaseNotFoundException(String message) {
        super(message);
    }
}