package com.sprint.bookinventorymgmt.usermgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedRoleAccessException extends RuntimeException {

    public UnauthorizedRoleAccessException(String message) {
        super(message);
    }

    public UnauthorizedRoleAccessException(Integer userId, Integer roleId) {
        super("User with id " + userId + " is not authorized to access role " + roleId + ".");
    }

    public UnauthorizedRoleAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
