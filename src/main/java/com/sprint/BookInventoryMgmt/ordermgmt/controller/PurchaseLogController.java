package com.sprint.BookInventoryMgmt.ordermgmt.controller;

import com.sprint.BookInventoryMgmt.common.ResponseStructure;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestdto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responsedto.PurchaseLogResponseDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.service.PurchaseLogService;
import com.sprint.BookInventoryMgmt.common.ResponseStructure;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseLogController {

    private final PurchaseLogService service;

    public PurchaseLogController(PurchaseLogService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseStructure<PurchaseLogResponseDTO> addPurchase(
            @RequestBody PurchaseLogRequestDTO dto) {

        PurchaseLogResponseDTO response = service.addPurchase(dto);

        return new ResponseStructure<>(
                201,
                "Purchase added successfully",
                response
        );
    }

    @GetMapping("/get")
    public ResponseStructure<List<PurchaseLogResponseDTO>> getAll() {

        List<PurchaseLogResponseDTO> list = service.getAll();

        return new ResponseStructure<>(
                200,
                "Fetched successfully",
                list
        );
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseStructure<String> delete(@PathVariable Long userId) {

        service.delete(userId);

        return new ResponseStructure<>(
                200,
                "Deleted successfully",
                null
        );
    }
}