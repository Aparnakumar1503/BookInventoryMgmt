package com.sprint.bookinventorymgmt.ordermgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class OrderProcessingException extends RuntimeException {

    public OrderProcessingException(String message) {
        super(message);
    }

    public OrderProcessingException(Integer userId, Integer inventoryId) {
        super("Failed to process order for userId: " + userId + " inventoryId: " + inventoryId);
    }

    public OrderProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
