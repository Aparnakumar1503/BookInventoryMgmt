package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InventoryPurchaseException extends RuntimeException {
    public InventoryPurchaseException(String message) {
        super(message);
    }

    public InventoryPurchaseException(Integer inventoryId) {
        super("Inventory already purchased with id: " + inventoryId);
    }

    public InventoryPurchaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
