package com.sprint.BookInventoryMgmt.orderMgmt.exceptions;


public class ShoppingCartNotFoundException extends RuntimeException {

    public ShoppingCartNotFoundException(String message) {

        super(message);
    }
}