package com.sprint.BookInventoryMgmt.OrderMgmt.Exception;


public class PurchaseNotFoundException extends RuntimeException {

    public PurchaseNotFoundException(String message) {
        super(message);
    }
}