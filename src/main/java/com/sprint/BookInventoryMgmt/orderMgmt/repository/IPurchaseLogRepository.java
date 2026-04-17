package com.sprint.BookInventoryMgmt.orderMgmt.repository;


import com.sprint.BookInventoryMgmt.orderMgmt.entity.PurchaseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPurchaseLogRepository extends JpaRepository<PurchaseLog, Long> {

    List<PurchaseLog> findByUserId(Long userId);

    List<PurchaseLog> findByInventoryId(Long inventoryId);
}