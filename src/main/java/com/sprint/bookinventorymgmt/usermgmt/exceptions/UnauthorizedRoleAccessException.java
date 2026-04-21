package com.sprint.bookinventorymgmt.usermgmt.exceptions;

public class UnauthorizedRoleAccessException extends RuntimeException {

    public UnauthorizedRoleAccessException(String message) {
        super(message);
    }
}
