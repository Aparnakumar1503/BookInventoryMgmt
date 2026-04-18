package com.sprint.BookInventoryMgmt.OrderMgmt.Repository;


import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.PurchaseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseLogRepository extends JpaRepository<PurchaseLog, Long> {

    List<PurchaseLog> findByUserId(Long userId);

    List<PurchaseLog> findByInventoryId(Long inventoryId);
}