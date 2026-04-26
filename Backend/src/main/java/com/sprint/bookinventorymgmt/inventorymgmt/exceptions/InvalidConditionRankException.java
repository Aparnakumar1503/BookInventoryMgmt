package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidConditionRankException extends RuntimeException {

    public InvalidConditionRankException(String message) {
        super(message);
    }

    public InvalidConditionRankException(Integer rank) {
        super("Invalid condition rank: " + rank);
    }

    public InvalidConditionRankException(String message, Throwable cause) {
        super(message, cause);
    }
}
