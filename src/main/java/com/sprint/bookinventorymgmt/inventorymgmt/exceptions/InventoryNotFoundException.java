package com.sprint.bookinventorymgmt.inventorymgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(String message) {
        super(message);
    }

    public InventoryNotFoundException(Integer inventoryId) {
        super("Inventory not found with id: " + inventoryId);
    }

    public InventoryNotFoundException(String isbn, boolean byIsbn) {
        super("No inventory found for ISBN: " + isbn);
    }

    public InventoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
