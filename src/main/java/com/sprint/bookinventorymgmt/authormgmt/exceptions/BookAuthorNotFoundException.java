package com.sprint.bookinventorymgmt.authormgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookAuthorNotFoundException extends RuntimeException  {
    public BookAuthorNotFoundException(String message) {
        super(message);
    }

    public BookAuthorNotFoundException(String isbn, boolean byIsbn) {
        super("No authors found for isbn: " + isbn);
    }

    public BookAuthorNotFoundException(Integer authorId) {
        super("No books found for authorId: " + authorId);
    }

    public BookAuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
