package com.sprint.bookinventorymgmt.usermgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PermRoleNotFoundException extends RuntimeException {
    public PermRoleNotFoundException(String message) {
        super(message);
    }

    public PermRoleNotFoundException(Integer roleNumber) {
        super("Role not found with id: " + roleNumber);
    }

    public PermRoleNotFoundException(String roleName, boolean byName) {
        super("Role not found with name: " + roleName);
    }

    public PermRoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
