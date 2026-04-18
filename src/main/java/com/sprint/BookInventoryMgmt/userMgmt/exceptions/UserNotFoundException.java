package com.sprint.BookInventoryMgmt.userMgmt.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
