package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.service.IPurchaseLogService;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseStructure<PurchaseLogResponseDTO> addPurchase(
            @RequestBody PurchaseLogRequestDTO requestDTO) {
        return ResponseBuilder.success(
                HttpStatus.CREATED.value(),
                "Purchase added successfully",
                service.addPurchase(requestDTO)
        );
    }

    @GetMapping
    public ResponseStructure<List<PurchaseLogResponseDTO>> getAll() {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Purchases fetched successfully",
                service.getAll()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseStructure<List<PurchaseLogResponseDTO>> getByUserId(@PathVariable Integer userId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Purchases fetched successfully for user",
                service.getByUserId(userId)
        );
    }

    @DeleteMapping("/{userId}/{inventoryId}")
    public ResponseStructure<String> delete(@PathVariable Integer userId,
                                            @PathVariable Integer inventoryId) {
        return ResponseBuilder.success(
                HttpStatus.OK.value(),
                "Purchase deleted successfully",
                service.delete(userId, inventoryId)
        );
    }
}
