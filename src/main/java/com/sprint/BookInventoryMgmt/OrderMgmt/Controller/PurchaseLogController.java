package com.sprint.BookInventoryMgmt.OrderMgmt.Controller;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.PurchaseLog;
import com.sprint.BookInventoryMgmt.OrderMgmt.Repository.PurchaseLogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseLogController {

    private final PurchaseLogRepository repo;

    public PurchaseLogController(PurchaseLogRepository repo) {
        this.repo = repo;
    }


    @PostMapping("/add")
    public PurchaseLog addPurchase(@RequestBody PurchaseLog purchase) {
        return repo.save(purchase);
    }

    @GetMapping("/get")
    public List<PurchaseLog> getAll() {
        return repo.findAll();
    }

    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable Long userId) {
        repo.deleteById(userId);
        return "Deleted Successfully";
    }
}