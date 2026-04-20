package com.sprint.BookInventoryMgmt.ordermgmt.exceptions;


public class PurchaseNotFoundException extends RuntimeException {

    public PurchaseNotFoundException(String message) {

        super(message);
    }
}