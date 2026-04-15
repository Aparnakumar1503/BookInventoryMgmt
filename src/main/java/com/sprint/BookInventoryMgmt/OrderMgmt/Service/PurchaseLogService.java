package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.PurchaseLog;
import com.sprint.BookInventoryMgmt.OrderMgmt.Repository.PurchaseLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseLogService {

    private final PurchaseLogRepository repo;

    public PurchaseLogService(PurchaseLogRepository repo) {
        this.repo = repo;
    }

    public PurchaseLog addPurchase(PurchaseLog purchase) {
        return repo.save(purchase);
    }

    public List<PurchaseLog> getAll() {
        return repo.findAll();
    }

    public String delete(Long userId) {
        repo.deleteById(userId);
        return "Deleted Successfully";
    }
}