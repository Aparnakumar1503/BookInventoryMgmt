package com.sprint.bookinventorymgmt.common;

public class ResponseBuilder {

    public static <T> ResponseStructure<T> success(int status, String message, T data) {
        return new ResponseStructure<>(status, message, data);
    }

    public static <T> ResponseStructure<T> error(int status, String message) {
        return new ResponseStructure<>(status, message, null);
    }
}