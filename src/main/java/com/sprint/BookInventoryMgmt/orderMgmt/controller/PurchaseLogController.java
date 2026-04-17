package com.sprint.BookInventoryMgmt.orderMgmt.controller;

import com.sprint.BookInventoryMgmt.common.ResponseStructure;
import com.sprint.BookInventoryMgmt.orderMgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.orderMgmt.dto.responseDto.PurchaseLogResponseDTO;
import com.sprint.BookInventoryMgmt.orderMgmt.service.IPurchaseLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseLogController {

    @Autowired
    IPurchaseLogService service;

    public PurchaseLogController(){

    }

    public PurchaseLogController(IPurchaseLogService service) {
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