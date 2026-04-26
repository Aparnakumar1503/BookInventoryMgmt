package com.sprint.bookinventorymgmt.usermgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Integer userId) {
        super("User not found with id: " + userId);
    }

    public UserNotFoundException(String userName, boolean byUserName) {
        super("User not found with username: " + userName);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
