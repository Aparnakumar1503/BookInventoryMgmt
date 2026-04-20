package com.sprint.BookInventoryMgmt.ordermgmt.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyPurchasedException extends RuntimeException {

    public BookAlreadyPurchasedException(String message) {
        super(message);
    }

    public BookAlreadyPurchasedException(Long bookId) {
        super("Book with ID " + bookId + " has already been purchased.");
    }

    public BookAlreadyPurchasedException(Long bookId, Long userId) {
        super("User ID " + userId + " has already purchased book with ID " + bookId + ".");
    }

    public BookAlreadyPurchasedException(String message, Throwable cause) {
        super(message, cause);
    }
}