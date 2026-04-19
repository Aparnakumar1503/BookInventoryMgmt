package com.sprint.bookinventorymgmt.ordermgmt.exceptions;


public class ShoppingCartNotFoundException extends RuntimeException {

    public ShoppingCartNotFoundException(String message) {
        super(message);
    }
}