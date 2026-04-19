package com.sprint.BookInventoryMgmt.ordermgmt.controller;

import com.sprint.BookInventoryMgmt.ordermgmt.service.IPurchaseLogService;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestdto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responsedto.PurchaseLogResponseDTO;
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
    public PurchaseLogResponseDTO addPurchase(@RequestBody PurchaseLogRequestDTO requestDTO) {
        return service.addPurchase(requestDTO);
    }

    @GetMapping("/get")
    public List<PurchaseLogResponseDTO> getAll() {
        return service.getAll();
    }

    // composite key — need both userId and inventoryId to delete
    @DeleteMapping("/delete/{userId}/{inventoryId}")
    public String delete(@PathVariable Integer userId,
                         @PathVariable Integer inventoryId) {
        return service.delete(userId, inventoryId);
    }
}