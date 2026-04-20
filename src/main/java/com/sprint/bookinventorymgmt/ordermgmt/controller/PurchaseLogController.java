package com.sprint.BookInventoryMgmt.ordermgmt.controller;

import com.sprint.BookInventoryMgmt.common.ResponseStructure;
import com.sprint.BookInventoryMgmt.ordermgmt.service.IPurchaseLogService;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseLogController {

    private final IPurchaseLogService service;

    public PurchaseLogController(IPurchaseLogService service) {
        this.service = service;
    }

    // ✅ ADD PURCHASE
    @PostMapping("/add")
    public ResponseStructure<PurchaseLogResponseDTO> addPurchase(
            @RequestBody PurchaseLogRequestDTO requestDTO) {

        PurchaseLogResponseDTO response = service.addPurchase(requestDTO);

        return ResponseStructure.of(200, "Purchase added successfully", response);
    }

    // ✅ GET ALL PURCHASES
    @GetMapping("/get")
    public ResponseStructure<List<PurchaseLogResponseDTO>> getAll() {

        List<PurchaseLogResponseDTO> list = service.getAll();

        return ResponseStructure.of(200, "Purchases fetched successfully", list);
    }

    // ✅ DELETE PURCHASE
    @DeleteMapping("/delete/{userId}/{inventoryId}")
    public ResponseStructure<String> delete(@PathVariable Integer userId,
                                            @PathVariable Integer inventoryId) {

        String msg = service.delete(userId, inventoryId);

        return ResponseStructure.of(200, "Purchase deleted successfully", msg);
    }
}