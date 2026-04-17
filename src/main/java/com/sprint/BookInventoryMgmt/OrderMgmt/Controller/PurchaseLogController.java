package com.sprint.BookInventoryMgmt.OrderMgmt.Controller;

import com.sprint.BookInventoryMgmt.OrderMgmt.DTO.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.DTO.PurchaseLogResponseDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.PurchaseLog;
import com.sprint.BookInventoryMgmt.OrderMgmt.Service.PurchaseLogService;

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
    public PurchaseLogResponseDTO addPurchase(@RequestBody PurchaseLogRequestDTO dto) {
        return service.addPurchase(dto);
    }

    @GetMapping("/get")
    public List<PurchaseLogResponseDTO> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable Long userId) {
        service.delete(userId);
        return "Deleted Successfully";
    }
}