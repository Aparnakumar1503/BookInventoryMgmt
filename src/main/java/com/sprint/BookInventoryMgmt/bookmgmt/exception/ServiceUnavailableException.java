package com.sprint.bookinventorymgmt.bookmgmt.exception;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}