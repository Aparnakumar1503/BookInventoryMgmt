package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.PurchaseLog;
import java.util.List;

public interface PurchaseLogService {

    PurchaseLog addPurchase(PurchaseLog purchase);

    List<PurchaseLog> getAll();

    String delete(Long userId);
}