package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.service.IPurchaseLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class PurchaseLogController {

    private final IPurchaseLogService service;

    public PurchaseLogController(IPurchaseLogService service) {
        this.service = service;
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<ResponseStructure<PurchaseLogResponseDTO>> checkout(
            @PathVariable Integer userId,
            @Valid @RequestBody PurchaseLogRequestDTO requestDTO) {
        requestDTO.setUserId(userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.CREATED.value(),
                                "Purchase created successfully",
                                service.addPurchase(requestDTO)
                        )
                );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseStructure<List<PurchaseLogResponseDTO>>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Purchases fetched successfully",
                        service.getByUserId(userId)
                )
        );
    }

    @DeleteMapping("/user/{userId}/inventory/{inventoryId}")
    public ResponseEntity<ResponseStructure<String>> delete(
            @PathVariable Integer userId,
            @PathVariable Integer inventoryId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Purchase deleted successfully",
                        service.delete(userId, inventoryId)
                )
        );
    }
}
