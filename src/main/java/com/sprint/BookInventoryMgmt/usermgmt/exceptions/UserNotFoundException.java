package com.sprint.BookInventoryMgmt.usermgmt.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
