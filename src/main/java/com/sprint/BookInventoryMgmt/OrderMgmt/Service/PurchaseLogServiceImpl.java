package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.PurchaseLog;
import com.sprint.BookInventoryMgmt.OrderMgmt.Exception.PurchaseNotFoundException;
import com.sprint.BookInventoryMgmt.OrderMgmt.Repository.PurchaseLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseLogServiceImpl implements PurchaseLogService {

    private final PurchaseLogRepository repo;

    public PurchaseLogServiceImpl(PurchaseLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public PurchaseLog addPurchase(PurchaseLog purchase) {
        return repo.save(purchase);
    }

    @Override
    public List<PurchaseLog> getAll() {
        return repo.findAll();
    }

    @Override
    public String delete(Long userId) {

        PurchaseLog purchase = repo.findById(userId)
                .orElseThrow(() ->
                        new PurchaseNotFoundException("Purchase not found for id: " + userId)
                );

        repo.delete(purchase);

        return "Deleted Successfully for id: " + userId;
    }
}