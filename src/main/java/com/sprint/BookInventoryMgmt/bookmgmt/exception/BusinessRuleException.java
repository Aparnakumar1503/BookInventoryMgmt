package com.sprint.bookinventorymgmt.bookmgmt.exception;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}