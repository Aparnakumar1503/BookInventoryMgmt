package com.sprint.BookInventoryMgmt.ordermgmt.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCartItemException extends RuntimeException {

    public DuplicateCartItemException(String message) {
        super(message);
    }

    public DuplicateCartItemException(Long bookId) {
        super("Item with book ID " + bookId + " already exists in the cart.");
    }

    public DuplicateCartItemException(Long cartId, Long bookId) {
        super("Book with ID " + bookId + " is already present in cart ID " + cartId + ".");
    }

    public DuplicateCartItemException(String message, Throwable cause) {
        super(message, cause);
    }
}