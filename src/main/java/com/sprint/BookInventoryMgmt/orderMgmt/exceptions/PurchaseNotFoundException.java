package com.sprint.BookInventoryMgmt.orderMgmt.exceptions;


public class PurchaseNotFoundException extends RuntimeException {

    public PurchaseNotFoundException(String message) {

        super(message);
    }
}