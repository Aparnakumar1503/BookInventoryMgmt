package com.sprint.bookinventorymgmt.usermgmt.exceptions;

public class UserDeleteNotAllowedException extends RuntimeException {

    public UserDeleteNotAllowedException(String message) {
        super(message);
    }
}
