package com.sprint.BookInventoryMgmt.ordermgmt.exception;


public class ShoppingCartNotFoundException extends RuntimeException {

    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}