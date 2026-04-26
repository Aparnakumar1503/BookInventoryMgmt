package com.sprint.bookinventorymgmt.usermgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException(String message) {
        super(message);
    }

    public DuplicateUsernameException(String userName, Throwable cause) {
        super("Username already exists: " + userName, cause);
    }

    public DuplicateUsernameException(Throwable cause) {
        super(cause);
    }
}
