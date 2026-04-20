package com.sprint.bookinventorymgmt.globalexception;

//import com.sprint.bookinventorymgmt.bookmgmt.exceptions.*;
//import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.*;
//import com.sprint.bookinventorymgmt.authormgmt.exceptions.*;
//import com.sprint.bookinventorymgmt.reviewmgmt.exceptions.*;
//import com.sprint.bookinventorymgmt.usermgmt.exceptions.*;
//import com.sprint.bookinventorymgmt.ordermgmt.exceptions.*;
//import com.sprint.bookinventorymgmt.common.ResponseStructure;
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── COMMON build() method ──────────────────────────────────────────────
//    private ResponseEntity<ResponseStructure<String>> build(HttpStatus status, String message) {
//        return ResponseEntity.status(status)
//                .body(ResponseStructure.of(status.value(), message, null)); // ← of() not builder()
//    }
//
//    // ── VALIDATION 1 — @Valid on @RequestBody ──────────────────────────────
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ResponseStructure<Map<String, String>>> handleValidation(
//            MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors()
//                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
//        return ResponseEntity.badRequest()
//                .body(ResponseStructure.of(400, "Validation failed", errors)); // ← of() not builder()
//    }
//
//    // ── VALIDATION 2 — @PathVariable / @RequestParam ───────────────────────
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ResponseStructure<Map<String, String>>> handleConstraintViolation(
//            ConstraintViolationException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getConstraintViolations()
//                .forEach(cv -> errors.put(
//                        cv.getPropertyPath().toString(),
//                        cv.getMessage()
//                ));
//        return ResponseEntity.badRequest()
//                .body(ResponseStructure.of(400, "Constraint violation", errors)); // ← of() not builder()
//    }
//
//    // ── 400 BAD REQUEST ────────────────────────────────────────────────────
//    @ExceptionHandler({
//            InvalidBookDataException.class,
//            InvalidInventoryDataException.class,
//            InvalidUserDataException.class,
//            InvalidIsbnFormatException.class,
//            InvalidConditionRankException.class,
//            InvalidPasswordException.class,
//            EmptyCartException.class,
//            InvalidRatingException.class
//    })
//    public ResponseEntity<ResponseStructure<String>> handleBadRequest(RuntimeException ex) {
//        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
//    }
//
//    // ── 403 FORBIDDEN ──────────────────────────────────────────────────────
//    @ExceptionHandler({
//            UnauthorizedRoleAccessException.class,
//            ReviewNotAllowedException.class
//    })
//    public ResponseEntity<ResponseStructure<String>> handleForbidden(RuntimeException ex) {
//        return build(HttpStatus.FORBIDDEN, ex.getMessage());
//    }
//
//    // ── 404 NOT FOUND ──────────────────────────────────────────────────────
//    @ExceptionHandler({
//            BookNotFoundException.class,
//            CategoryNotFoundException.class,
//            PublisherNotFoundException.class,
//            AuthorNotFoundException.class,
//            BookAuthorNotFoundException.class,
//            InventoryNotFoundException.class,
//            BookConditionNotFoundException.class,
//            ReviewerNotFoundException.class,
//            BookReviewNotFoundException.class,
//            UserNotFoundException.class,
//            RoleNotFoundException.class,
//            PurchaseNotFoundException.class,
//            ShoppingCartNotFoundException.class
//    })
//    public ResponseEntity<ResponseStructure<String>> handleNotFound(RuntimeException ex) {
//        return build(HttpStatus.NOT_FOUND, ex.getMessage());
//    }
//
//    // ── 409 CONFLICT ───────────────────────────────────────────────────────
//    @ExceptionHandler({
//            DuplicateBookException.class,
//            DuplicateAuthorException.class,
//            DuplicateBookAuthorException.class,
//            DuplicateInventoryException.class,
//            InventoryPurchaseException.class,
//            DuplicateUsernameException.class,
//            DuplicateReviewException.class,
//            BookAlreadyPurchasedException.class,
//            BookNotAvailableException.class,
//            DuplicateCartItemException.class
//    })
//    public ResponseEntity<ResponseStructure<String>> handleConflict(RuntimeException ex) {
//        return build(HttpStatus.CONFLICT, ex.getMessage());
//    }
//
//    // ── 422 UNPROCESSABLE ──────────────────────────────────────────────────
//    @ExceptionHandler({
//            InsufficientInventoryException.class,
//            CheckoutFailedException.class,
//            OrderProcessingException.class
//    })
//    public ResponseEntity<ResponseStructure<String>> handleUnprocessable(RuntimeException ex) {
//        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
//    }
//
//    // ── 500 INTERNAL SERVER ERROR ──────────────────────────────────────────
//    @ExceptionHandler(DatabaseOperationException.class)
//    public ResponseEntity<ResponseStructure<String>> handleDatabase(RuntimeException ex) {
//        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//    }
//
//    // ── GENERIC FALLBACK ───────────────────────────────────────────────────
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ResponseStructure<String>> handleGeneric(Exception ex) {
//        return build(HttpStatus.INTERNAL_SERVER_ERROR,
//                "An unexpected error occurred: " + ex.getMessage());
//    }
}