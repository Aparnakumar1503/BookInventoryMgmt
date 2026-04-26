package com.sprint.bookinventorymgmt.bookmgmt.exceptions;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}