package com.sprint.bookinventorymgmt.globalexception;

import com.sprint.bookinventorymgmt.authormgmt.exceptions.AuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.BookAuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateBookAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.InvalidBookDataException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.BookAlreadyExistsException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.BookNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.CategoryNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.InvalidIsbnFormatException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.PublisherNotFoundException;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.BookConditionNotFoundException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.DatabaseOperationException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.DuplicateInventoryException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InsufficientInventoryException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InvalidConditionRankException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InvalidInventoryDataException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InventoryNotFoundException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InventoryPurchaseException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.BookAlreadyPurchasedException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.BookNotAvailableException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.DuplicateCartItemException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.EmptyCartException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.PurchaseNotFoundException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.ShoppingCartNotFoundException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.DuplicateReviewException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.InvalidRatingException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.ReviewNotAllowedException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.ReviewNotFoundException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.ReviewerNotFoundException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.DuplicateUsernameException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.PermRoleNotFoundException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ResponseStructure<String>> build(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(ResponseBuilder.error(status.value(), message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest()
                .body(ResponseBuilder.error(400, "Validation failed", errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleConstraintViolation(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations()
                .forEach(cv -> errors.put(cv.getPropertyPath().toString(), cv.getMessage()));
        return ResponseEntity.badRequest()
                .body(ResponseBuilder.error(400, "Constraint violation", errors));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleHandlerMethodValidation(
            HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllValidationResults().forEach(result -> {
            String parameterName = result.getMethodParameter().getParameterName();
            String key = parameterName != null ? parameterName : result.getMethodParameter().toString();
            String message = result.getResolvableErrors().stream()
                    .map(MessageSourceResolvable::getDefaultMessage)
                    .filter(value -> value != null && !value.isBlank())
                    .findFirst()
                    .orElse("Invalid value");
            errors.put(key, message);
        });

        return ResponseEntity.badRequest()
                .body(ResponseBuilder.error(400, "Validation failed", errors));
    }

    @ExceptionHandler({
            InvalidBookDataException.class,
            InvalidInventoryDataException.class,
            InvalidIsbnFormatException.class,
            InvalidConditionRankException.class,
            EmptyCartException.class,
            InvalidRatingException.class
    })
    public ResponseEntity<ResponseStructure<String>> handleBadRequest(RuntimeException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            ReviewNotAllowedException.class
    })
    public ResponseEntity<ResponseStructure<String>> handleForbidden(RuntimeException ex) {
        return build(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler({
            BookNotFoundException.class,
            CategoryNotFoundException.class,
            PublisherNotFoundException.class,
            AuthorNotFoundException.class,
            BookAuthorNotFoundException.class,
            InventoryNotFoundException.class,
            BookConditionNotFoundException.class,
            ReviewerNotFoundException.class,
            ReviewNotFoundException.class,
            UserNotFoundException.class,
            PermRoleNotFoundException.class,
            PurchaseNotFoundException.class,
            ShoppingCartNotFoundException.class
    })
    public ResponseEntity<ResponseStructure<String>> handleNotFound(RuntimeException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({
            BookAlreadyExistsException.class,
            DuplicateAuthorException.class,
            DuplicateBookAuthorException.class,
            DuplicateInventoryException.class,
            InventoryPurchaseException.class,
            DuplicateUsernameException.class,
            DuplicateReviewException.class,
            BookAlreadyPurchasedException.class,
            BookNotAvailableException.class,
            DuplicateCartItemException.class
    })
    public ResponseEntity<ResponseStructure<String>> handleConflict(RuntimeException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<ResponseStructure<String>> handleUnprocessable(RuntimeException ex) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<ResponseStructure<String>> handleDatabase(RuntimeException ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleGeneric(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred: " + ex.getMessage());
    }
}
