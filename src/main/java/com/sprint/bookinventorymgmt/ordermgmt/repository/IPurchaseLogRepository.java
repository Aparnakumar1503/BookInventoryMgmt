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

// Marks this interface as a Spring Data repository component
// WHY: Allows Spring to automatically detect and create bean implementation at runtime
@Repository

// Extends JpaRepository to get built-in CRUD operations without writing implementation
// WHY: Saves boilerplate code like save(), findAll(), deleteById(), etc.
public interface IPurchaseLogRepository extends JpaRepository<PurchaseLog, PurchaseLogId> {

    // Derived query method using Spring naming convention
    // WHY: Automatically generates SQL to find records by userId without writing custom query
    List<PurchaseLog> findByIdUserId(Integer userId);

    // Derived query method to fetch purchases by inventoryId
    // WHY: Helps in fetching all users who purchased a specific book/item
    List<PurchaseLog> findByIdInventoryId(Integer inventoryId);

    // Custom JPQL query to fetch a specific purchase record
    // WHY: Used when method naming is not enough for complex composite key conditions
    @Query("SELECT p FROM PurchaseLog p WHERE p.id.userId = :userId AND p.id.inventoryId = :inventoryId")
    PurchaseLog findByUserIdAndInventoryId(
            @Param("userId") Integer userId,
            @Param("inventoryId") Integer inventoryId);

    // Custom JPQL query to count total purchases of a user
    // WHY: Used for analytics or business logic like "how many books user purchased"
    @Query("SELECT COUNT(p) FROM PurchaseLog p WHERE p.id.userId = :userId")
    Long countByUserId(@Param("userId") Integer userId);

    // Indicates that this query modifies data (DELETE/UPDATE)
    // WHY: Required for non-select queries, otherwise Spring will throw exception
    @Modifying

    // Ensures transaction management for delete operation
    // WHY: Needed because DELETE must be executed inside a transaction
    @Transactional

    // Custom delete query using composite key
    // WHY: Allows precise deletion without loading entity into memory
    @Query("DELETE FROM PurchaseLog p WHERE p.id.userId = :userId AND p.id.inventoryId = :inventoryId")
    void deleteByUserIdAndInventoryId(
            @Param("userId") Integer userId,
            @Param("inventoryId") Integer inventoryId);
}