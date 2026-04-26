package com.sprint.bookinventorymgmt.usermgmt.exceptions;

public class PasswordReuseException extends RuntimeException {

    public PasswordReuseException(String message) {
        super(message);
    }
}
