package com.sprint.BookInventoryMgmt.ordermgmt.repository;


import com.sprint.BookInventoryMgmt.ordermgmt.entity.PurchaseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseLogRepository extends JpaRepository<PurchaseLog, Long> {

    List<PurchaseLog> findByUserId(Long userId);

    List<PurchaseLog> findByInventoryId(Long inventoryId);
}