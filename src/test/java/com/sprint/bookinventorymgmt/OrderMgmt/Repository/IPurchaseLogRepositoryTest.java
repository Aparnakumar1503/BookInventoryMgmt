package com.sprint.bookinventorymgmt.ordermgmt.repository;

import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IPurchaseLogRepositoryTest {

    @Autowired
    private IPurchaseLogRepository repository;

    @Test
    void testSavePurchaseLog() {
        PurchaseLog purchaseLog = new PurchaseLog(1, 1000000);
        PurchaseLog saved = repository.saveAndFlush(purchaseLog);

        assertNotNull(saved);
        assertEquals(1, saved.getUserId());
        assertEquals(1000000, saved.getInventoryId());
    }

    @Test
    void testFindByUserId() {
        repository.saveAndFlush(new PurchaseLog(1, 1000000));
        repository.saveAndFlush(new PurchaseLog(1, 1000001));

        List<PurchaseLog> result = repository.findByIdUserId(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getUserId());
    }

    @Test
    void testFindByInventoryId() {
        repository.saveAndFlush(new PurchaseLog(1, 1000000));
        repository.saveAndFlush(new PurchaseLog(2, 1000001));

        List<PurchaseLog> result = repository.findByIdInventoryId(1000000);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1000000, result.get(0).getInventoryId());
    }

    // Custom Query 1 — find by userId and inventoryId
    @Test
    void testFindByUserIdAndInventoryId() {
        repository.saveAndFlush(new PurchaseLog(1, 1000000));

        PurchaseLog result = repository.findByUserIdAndInventoryId(1, 1000000);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals(1000000, result.getInventoryId());
    }

    // Custom Query 2 — count by userId
    @Test
    void testCountByUserId() {
        repository.saveAndFlush(new PurchaseLog(1, 1000000));
        repository.saveAndFlush(new PurchaseLog(1, 1000001));
        repository.saveAndFlush(new PurchaseLog(1, 1000002));

        Long count = repository.countByUserId(1);

        assertNotNull(count);
        assertEquals(3L, count);
    }

    // Custom Query 3 — delete by userId and inventoryId
    @Test
    void testDeleteByUserIdAndInventoryId() {
        repository.saveAndFlush(new PurchaseLog(1, 1000000));
        repository.saveAndFlush(new PurchaseLog(1, 1000001));

        repository.deleteByUserIdAndInventoryId(1, 1000000);

        List<PurchaseLog> result = repository.findByIdUserId(1);

        assertEquals(1, result.size());                            // only 1 item remaining
        assertEquals(1000001, result.get(0).getInventoryId());     // correct item remains
    }
}