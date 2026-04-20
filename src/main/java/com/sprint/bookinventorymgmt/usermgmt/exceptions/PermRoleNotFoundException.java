package com.sprint.bookinventorymgmt.usermgmt.exceptions;

public class PermRoleNotFoundException extends RuntimeException {
    public PermRoleNotFoundException(String message) {
        super(message);
    }
    public PermRoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
