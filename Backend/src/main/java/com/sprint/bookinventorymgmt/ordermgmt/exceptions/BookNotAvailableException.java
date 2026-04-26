package com.sprint.bookinventorymgmt.ordermgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException(String message) {
        super(message);
    }

    public BookNotAvailableException(String isbn, boolean byIsbn) {
        super("Book is not available for isbn: " + isbn);
    }

    public BookNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
