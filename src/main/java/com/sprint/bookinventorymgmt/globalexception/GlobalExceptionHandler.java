package com.sprint.bookinventorymgmt.globalexception;

import com.sprint.bookinventorymgmt.bookmgmt.exception.*;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ================= BOOK =================

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleBookNotFound(BookNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseBuilder.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<ResponseStructure<String>> handleBookAlreadyExists(BookAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ResponseBuilder.error(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    // ================= CATEGORY =================

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleCategoryNotFound(CategoryNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseBuilder.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    // ================= PUBLISHER =================

    @ExceptionHandler(PublisherNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handlePublisherNotFound(PublisherNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseBuilder.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    // ================= STATE =================

    @ExceptionHandler(StateNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleStateNotFound(StateNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseBuilder.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    // ================= VALIDATION =================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseBuilder.error(
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation failed",
                        errors
                ));
    }

    @ExceptionHandler(EmptyDataException.class)
    public ResponseEntity<ResponseStructure<Object>> handleEmpty(EmptyDataException ex) {
        return new ResponseEntity<>(
                ResponseBuilder.error(HttpStatus.NO_CONTENT.value(), ex.getMessage()),
                HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ResponseStructure<Object>> handleInvalid(InvalidDataException ex) {
        return new ResponseEntity<>(
                ResponseBuilder.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceDeletionException.class)
    public ResponseEntity<ResponseStructure<Object>> handleDelete(ResourceDeletionException ex) {
        return new ResponseEntity<>(
                ResponseBuilder.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ResponseStructure<Object>> handleInvalidInput(InvalidInputException ex) {
        return new ResponseEntity<>(
                ResponseBuilder.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseStructure<Object>> handleDataNotFound(DataNotFoundException ex) {
        return new ResponseEntity<>(
                ResponseBuilder.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ResponseStructure<String>> handleBusinessRule(BusinessRuleException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ResponseBuilder.error(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<ResponseStructure<String>> handleDataIntegrity(DataIntegrityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseBuilder.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ResponseStructure<String>> handleServiceUnavailable(ServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ResponseBuilder.error(HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage()));
    }
    // ================= BAD REQUEST =================

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseStructure<String>> handleIllegalArgument(IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseBuilder.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    // ================= GENERIC =================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleGeneric(Exception ex) {

        log.error("Unexpected error occurred: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseBuilder.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Something went wrong. Please contact support."
                ));
    }
}