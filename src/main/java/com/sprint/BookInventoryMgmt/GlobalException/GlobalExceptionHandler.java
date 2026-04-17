package com.sprint.BookInventoryMgmt.GlobalException;

import com.sprint.BookInventoryMgmt.BookMgmt.Exception.*;
import com.sprint.BookInventoryMgmt.InventoryMgmt.exception.*;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ResponseStructure<Object>> buildResponse(String message, HttpStatus status) {

        ResponseStructure<Object> response = new ResponseStructure<>();
        response.setStatusCode(status.value());
        response.setMessage(message);
        response.setData(null);

        return new ResponseEntity<>(response, status);
    }

    // BOOK
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ResponseStructure<Object>> handleBookNotFound(BookNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ResponseStructure<Object>> handleCategoryNotFound(CategoryNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PublisherNotFoundException.class)
    public ResponseEntity<ResponseStructure<Object>> handlePublisherNotFound(PublisherNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StateNotFoundException.class)
    public ResponseEntity<ResponseStructure<Object>> handleStateNotFound(StateNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // INVENTORY
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ResponseStructure<Object>> handleInventoryNotFound(InventoryNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookConditionNotFoundException.class)
    public ResponseEntity<ResponseStructure<Object>> handleBookConditionNotFound(BookConditionNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateInventoryException.class)
    public ResponseEntity<ResponseStructure<Object>> handleDuplicateInventory(DuplicateInventoryException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidInventoryDataException.class)
    public ResponseEntity<ResponseStructure<Object>> handleInvalidData(InvalidInventoryDataException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InventoryPurchaseException.class)
    public ResponseEntity<ResponseStructure<Object>> handlePurchaseException(InventoryPurchaseException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<ResponseStructure<Object>> handleDatabaseError(DatabaseOperationException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // VALIDATION
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStructure<Object>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ResponseStructure<Object> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Validation Failed");
        response.setData(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // GENERIC
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<Object>> handleGeneric(Exception ex) {
        return buildResponse("Something went wrong. Please contact support.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}