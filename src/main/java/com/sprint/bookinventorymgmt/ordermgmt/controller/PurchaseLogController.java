package com.sprint.bookinventorymgmt.ordermgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.service.IPurchaseLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseLogController {

    private final IPurchaseLogService service;

    public PurchaseLogController(IPurchaseLogService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseStructure<PurchaseLogResponseDTO>> addPurchase(
            @RequestBody PurchaseLogRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.CREATED.value(),
                                "Purchase created successfully",
                                service.addPurchase(requestDTO)
                        )
                );
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseStructure<List<PurchaseLogResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Purchases fetched successfully",
                        service.getAll()
                )
        );
    }

    @DeleteMapping("/delete/{userId}/{inventoryId}")
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
