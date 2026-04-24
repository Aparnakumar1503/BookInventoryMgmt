package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.service.IPurchaseLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles purchase and order endpoints so checkout logic stays isolated from shopping cart operations.
 */
@RestController
public class PurchaseLogController {

    private final IPurchaseLogService purchaseLogService;

    public PurchaseLogController(IPurchaseLogService purchaseLogService) {
        this.purchaseLogService = purchaseLogService;
    }

    /**
     * Creates a purchase during checkout for the supplied user.
     */
    @PostMapping("/api/v1/orders/checkout/{userId}")
    public ResponseEntity<ResponseStructure<PurchaseLogResponseDTO>> checkout(
            @PathVariable Integer userId,
            @Valid @RequestBody PurchaseLogRequestDTO requestDTO) {
        requestDTO.setUserId(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(
                        HttpStatus.CREATED.value(),
                        "Purchase created successfully",
                        purchaseLogService.addPurchase(requestDTO)
                ));
    }

    /**
     * Returns purchases for one user.
     */
    @GetMapping("/api/v1/orders/user/{userId}")
    public ResponseEntity<ResponseStructure<PaginatedResponse<PurchaseLogResponseDTO>>> getOrdersByUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Purchases fetched successfully",
                        PaginationUtils.paginate(purchaseLogService.getByUserId(userId), page, size)
                )
        );
    }
}
