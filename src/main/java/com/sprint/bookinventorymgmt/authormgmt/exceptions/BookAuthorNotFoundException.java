package com.sprint.bookinventorymgmt.authormgmt.exceptions;

public class BookAuthorNotFoundException extends RuntimeException  {
    public BookAuthorNotFoundException(String message) {
        super(message);
    }

    public BookAuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
