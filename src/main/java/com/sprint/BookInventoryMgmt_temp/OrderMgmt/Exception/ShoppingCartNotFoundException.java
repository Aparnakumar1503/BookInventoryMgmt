package com.sprint.BookInventoryMgmt.OrderMgmt.Exception;


public class ShoppingCartNotFoundException extends RuntimeException {

    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}