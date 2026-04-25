package com.sprint.bookinventorymgmt.common;

import java.time.LocalDateTime;

public class ResponseStructure<T> {

    private int statusCode;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ResponseStructure() {
        this.timestamp = LocalDateTime.now();
    }

    public ResponseStructure(int statusCode, String message, T data) {
        this(statusCode, message, data, LocalDateTime.now());
    }

    public ResponseStructure(int statusCode, String message, T data, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    // GETTERS & SETTERS

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}