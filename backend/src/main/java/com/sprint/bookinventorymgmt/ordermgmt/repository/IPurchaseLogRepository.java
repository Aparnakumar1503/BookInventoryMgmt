package com.sprint.bookinventorymgmt.ordermgmt.repository;

import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLog;
import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLogId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IPurchaseLogRepository extends JpaRepository<PurchaseLog, PurchaseLogId> {

    // for repo tests
    List<PurchaseLog> findByIdUserId(Integer userId);
    List<PurchaseLog> findByIdInventoryId(Integer inventoryId);

    // Custom Query 1 — Find specific purchase by userId and inventoryId
    @Query("SELECT p FROM PurchaseLog p WHERE p.id.userId = :userId AND p.id.inventoryId = :inventoryId")
    PurchaseLog findByUserIdAndInventoryId(@Param("userId") Integer userId, @Param("inventoryId") Integer inventoryId);

    // Custom Query 2 — Count total purchases by userId
    @Query("SELECT COUNT(p) FROM PurchaseLog p WHERE p.id.userId = :userId")
    Long countByUserId(@Param("userId") Integer userId);

    // Custom Query 3 — Delete specific purchase by userId and inventoryId
    @Modifying
    @Transactional
    @Query("DELETE FROM PurchaseLog p WHERE p.id.userId = :userId AND p.id.inventoryId = :inventoryId")
    void deleteByUserIdAndInventoryId(@Param("userId") Integer userId, @Param("inventoryId") Integer inventoryId);
}