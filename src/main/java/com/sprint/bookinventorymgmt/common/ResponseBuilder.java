package com.sprint.bookinventorymgmt.common;

import java.time.LocalDateTime;

public class ResponseBuilder {

    // SUCCESS
    public static <T> ResponseStructure<T> success(int status, String message, T data) {
        return new ResponseStructure<>(status, message, data, LocalDateTime.now());
    }

    // ERROR (NO DATA)
    public static <T> ResponseStructure<T> error(int status, String message) {
        return new ResponseStructure<>(status, message, null, LocalDateTime.now());
    }

    // ERROR WITH DATA
    public static <T> ResponseStructure<T> error(int status, String message, T data) {
        return new ResponseStructure<>(status, message, data, LocalDateTime.now());
    }
}