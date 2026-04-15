package com.sprint.BookInventoryMgmt.OrderMgmt.Controller;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.PurchaseLog;
import com.sprint.BookInventoryMgmt.OrderMgmt.Repository.PurchaseLogRepository;
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
    public PurchaseLog addPurchase(@RequestBody PurchaseLog purchase) {
        return service.addPurchase(purchase);
    }

    @GetMapping("/get")
    public List<PurchaseLog> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable Long userId) {
        service.delete(userId);
        return "Deleted Successfully";
    }
}