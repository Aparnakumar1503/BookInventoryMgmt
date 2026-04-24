package com.sprint.bookinventorymgmt.globalexception;

import com.sprint.bookinventorymgmt.authormgmt.exceptions.AuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.BookAuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateBookAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.InvalidBookDataException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.BookAlreadyExistsException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.BookNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.CategoryNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.DataNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.InvalidInputException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.InvalidIsbnFormatException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.PublisherNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.StateNotFoundException;
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
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.CheckoutFailedException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.DuplicateCartItemException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.EmptyCartException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.OrderProcessingException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.PurchaseNotFoundException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.ShoppingCartNotFoundException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.DuplicateReviewException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.InvalidRatingException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.InvalidReviewerDataException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.ReviewNotAllowedException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.ReviewNotFoundException;
import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.ReviewerNotFoundException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.DuplicateUsernameException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.InvalidCredentialsException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.PermRoleNotFoundException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.UnauthorizedRoleAccessException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Creates a consistent simple error response for domain-level exceptions.
     */
    private ResponseEntity<ResponseStructure<String>> build(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(ResponseBuilder.error(status.value(), message));
    }

    /**
     * Creates an error response with structured details for request parsing and framework failures.
     */
    private ResponseEntity<ResponseStructure<Map<String, String>>> build(
            HttpStatus status,
            String message,
            Map<String, String> details) {
        return ResponseEntity.status(status)
                .body(ResponseBuilder.error(status.value(), message, details));
    }

    /**
     * Creates a one-line detail map so the client can display the exact failure reason.
     */
    private Map<String, String> detail(String key, String value) {
        Map<String, String> details = new LinkedHashMap<>();
        details.put(key, value);
        return details;
    }

    /**
     * Returns the deepest useful message available from nested exceptions.
     */
    private String mostSpecificMessage(Throwable throwable, String fallback) {
        Throwable current = throwable;
        while (current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }
        return current.getMessage() == null || current.getMessage().isBlank()
                ? fallback
                : current.getMessage();
    }

    /**
     * Collects request-body validation errors from DTO annotations such as @NotBlank and @NotNull.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return build(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    }

    /**
     * Collects validation failures raised on path variables and request parameters.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleConstraintViolation(
            ConstraintViolationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations()
                .forEach(error -> errors.put(error.getPropertyPath().toString(), error.getMessage()));
        return build(HttpStatus.BAD_REQUEST, "Constraint violation", errors);
    }

    /**
     * Collects binding errors raised when request parameters cannot be mapped to controller arguments.
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleBindException(BindException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return build(HttpStatus.BAD_REQUEST, "Request parameter binding failed", errors);
    }

    /**
     * Reports authentication failures with the exact login or security error.
     */
    @ExceptionHandler({
            AuthenticationException.class,
            InvalidCredentialsException.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<ResponseStructure<String>> handleAuthentication(Exception ex) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    /**
     * Handles invalid input and request-shape problems raised by business services.
     */
    @ExceptionHandler({
            InvalidBookDataException.class,
            InvalidInventoryDataException.class,
            InvalidIsbnFormatException.class,
            InvalidConditionRankException.class,
            EmptyCartException.class,
            InvalidRatingException.class,
            InvalidInputException.class,
            InvalidReviewerDataException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ResponseStructure<String>> handleBadRequest(RuntimeException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Handles authorization failures raised by the application business rules.
     */
    @ExceptionHandler({
            UnauthorizedRoleAccessException.class,
            ReviewNotAllowedException.class
    })
    public ResponseEntity<ResponseStructure<String>> handleForbidden(RuntimeException ex) {
        return build(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    /**
     * Handles security-layer access denial for protected resources.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseStructure<String>> handleAccessDenied(AccessDeniedException ex) {
        return build(HttpStatus.FORBIDDEN, "You are not allowed to access this resource.");
    }

    /**
     * Handles entity and collection not-found cases with explicit domain messages.
     */
    @ExceptionHandler({
            BookNotFoundException.class,
            CategoryNotFoundException.class,
            PublisherNotFoundException.class,
            StateNotFoundException.class,
            AuthorNotFoundException.class,
            BookAuthorNotFoundException.class,
            InventoryNotFoundException.class,
            BookConditionNotFoundException.class,
            ReviewerNotFoundException.class,
            ReviewNotFoundException.class,
            UserNotFoundException.class,
            PermRoleNotFoundException.class,
            PurchaseNotFoundException.class,
            ShoppingCartNotFoundException.class,
            DataNotFoundException.class,
            EmptyResultDataAccessException.class,
            EntityNotFoundException.class
    })
    public ResponseEntity<ResponseStructure<String>> handleNotFound(Exception ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Handles duplicate and state-conflict scenarios where the request cannot proceed as-is.
     */
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

    /**
     * Handles checkout and inventory processing failures that are valid requests but cannot be completed.
     */
    @ExceptionHandler({
            InsufficientInventoryException.class,
            CheckoutFailedException.class,
            OrderProcessingException.class
    })
    public ResponseEntity<ResponseStructure<String>> handleUnprocessable(RuntimeException ex) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    /**
     * Handles malformed JSON and unreadable bodies so the client knows the payload shape is wrong.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleMessageNotReadable(
            HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST, "Request body is malformed or unreadable",
                detail("reason", mostSpecificMessage(ex, "Check the JSON syntax and field value types.")));
    }

    /**
     * Handles wrong parameter or path-variable types such as text being sent where an integer is required.
     */
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            ConversionFailedException.class
    })
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleTypeMismatch(Exception ex) {
        String reason = ex instanceof MethodArgumentTypeMismatchException mismatch
                ? "Parameter '" + mismatch.getName() + "' must be of type " +
                (mismatch.getRequiredType() == null ? "the expected type" : mismatch.getRequiredType().getSimpleName())
                : mostSpecificMessage(ex, "One or more values have an invalid type.");
        return build(HttpStatus.BAD_REQUEST, "Request contains invalid parameter types", detail("reason", reason));
    }

    /**
     * Handles missing query parameters, path variables, and required headers.
     */
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MissingPathVariableException.class,
            MissingRequestHeaderException.class
    })
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleMissingRequestData(Exception ex) {
        String reason;
        if (ex instanceof MissingServletRequestParameterException missingParam) {
            reason = "Required request parameter '" + missingParam.getParameterName() + "' is missing.";
        } else if (ex instanceof MissingPathVariableException missingPathVariable) {
            reason = "Required path variable '" + missingPathVariable.getVariableName() + "' is missing.";
        } else if (ex instanceof MissingRequestHeaderException missingHeader) {
            reason = "Required header '" + missingHeader.getHeaderName() + "' is missing.";
        } else {
            reason = "A required request value is missing.";
        }
        return build(HttpStatus.BAD_REQUEST, "Request is missing required data", detail("reason", reason));
    }

    /**
     * Handles unsupported HTTP methods with an explicit method-level explanation.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, "HTTP method not supported",
                detail("reason", "Method '" + ex.getMethod() + "' is not allowed for this endpoint."));
    }

    /**
     * Handles unsupported content types such as sending XML to a JSON-only endpoint.
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex) {
        return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Content type is not supported",
                detail("reason", "Supported content type is application/json."));
    }

    /**
     * Handles missing routes and static-resource misses with a clean API message.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleNoResourceFound(NoResourceFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "Endpoint not found",
                detail("reason", "No handler exists for " + ex.getHttpMethod() + " " + ex.getResourcePath()));
    }

    /**
     * Handles transaction failures caused by validation and persistence issues.
     */
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleTransactionSystemException(
            TransactionSystemException ex) {
        return build(HttpStatus.BAD_REQUEST, "Transaction could not be completed",
                detail("reason", mostSpecificMessage(ex, "The request failed during transaction processing.")));
    }

    /**
     * Handles direct database-constraint conflicts such as unique and foreign-key violations.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {
        return build(HttpStatus.CONFLICT, "Database constraint violation",
                detail("reason", mostSpecificMessage(ex, "The request conflicts with existing or related data.")));
    }

    /**
     * Handles broader persistence failures while preserving a useful reason for the client.
     */
    @ExceptionHandler({
            DatabaseOperationException.class,
            DataAccessException.class
    })
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleDatabase(RuntimeException ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Database operation failed",
                detail("reason", mostSpecificMessage(ex, "A database operation failed.")));
    }

    /**
     * Catches any remaining unhandled failures and still returns the most specific reason available.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleGeneric(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected server error occurred",
                detail("reason", mostSpecificMessage(ex, ex.getClass().getSimpleName())));
    }
}
