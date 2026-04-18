package com.sprint.BookInventoryMgmt.ordermgmt.controller;

import com.sprint.BookInventoryMgmt.ordermgmt.service.PurchaseLogService;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestdto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responsedto.PurchaseLogResponseDTO;
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
    public PurchaseLogResponseDTO addPurchase(@RequestBody PurchaseLogRequestDTO requestDTO) {
        return service.addPurchase(requestDTO);
    }

    @GetMapping("/get")
    public List<PurchaseLogResponseDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable Long userId) {
        return service.delete(userId);
    }
}