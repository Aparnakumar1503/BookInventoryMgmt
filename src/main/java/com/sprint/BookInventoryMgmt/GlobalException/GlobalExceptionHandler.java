package com.sprint.BookInventoryMgmt.GlobalException;

import com.sprint.BookInventoryMgmt.BookMgmt.Exception.BookNotFoundException;
import com.sprint.BookInventoryMgmt.BookMgmt.Exception.CategoryNotFoundException;
import com.sprint.BookInventoryMgmt.BookMgmt.Exception.PublisherNotFoundException;
import com.sprint.BookInventoryMgmt.BookMgmt.Exception.StateNotFoundException;
import com.sprint.BookInventoryMgmt.InventoryMgmt.exception.InventoryNotFoundException;
import com.sprint.BookInventoryMgmt.InventoryMgmt.exception.BookConditionNotFoundException;
import com.sprint.BookInventoryMgmt.InventoryMgmt.exception.DuplicateInventoryException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // BOOK
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFound(BookNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // CATEGORY
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFound(CategoryNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // PUBLISHER
    @ExceptionHandler(PublisherNotFoundException.class)
    public ResponseEntity<Object> handlePublisherNotFound(PublisherNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // STATE
    @ExceptionHandler(StateNotFoundException.class)
    public ResponseEntity<Object> handleStateNotFound(StateNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // VALIDATION
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // GENERIC (SAFE VERSION)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        return buildResponse("Something went wrong. Please contact support.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // COMMON
    private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }

    // INVENTORY
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<Object> handleInventoryNotFound(InventoryNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // BOOK CONDITION
    @ExceptionHandler(BookConditionNotFoundException.class)
    public ResponseEntity<Object> handleBookConditionNotFound(BookConditionNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // DUPLICATE INVENTORY (optional)
    @ExceptionHandler(DuplicateInventoryException.class)
    public ResponseEntity<Object> handleDuplicateInventory(DuplicateInventoryException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}