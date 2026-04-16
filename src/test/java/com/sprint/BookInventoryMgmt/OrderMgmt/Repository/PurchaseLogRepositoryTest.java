package com.sprint.BookInventoryMgmt.OrderMgmt.Repository;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.PurchaseLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PurchaseLogRepositoryTest {

    @Autowired
    private PurchaseLogRepository repository;

    @Test
    void testSavePurchaseLog() {
        PurchaseLog log = new PurchaseLog();
        log.setUserId(1L);
        log.setInventoryId(101L);


        PurchaseLog saved = repository.save(log);

        assertNotNull(saved);
        assertEquals(1L, saved.getUserId());
    }

    @Test
    void testFindByUserId() {
        PurchaseLog log1 = new PurchaseLog();
        log1.setUserId(1L);
        log1.setInventoryId(101L);


        PurchaseLog log2 = new PurchaseLog();
        log2.setUserId(2L);
        log2.setInventoryId(102L);


        repository.save(log1);
        repository.save(log2);

        List<PurchaseLog> result = repository.findByUserId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
    }

    @Test
    void testFindByInventoryId() {
        PurchaseLog log1 = new PurchaseLog();
        log1.setUserId(1L);
        log1.setInventoryId(200L);


        PurchaseLog log2 = new PurchaseLog();
        log2.setUserId(2L);
        log2.setInventoryId(300L);


        repository.save(log1);
        repository.save(log2);

        List<PurchaseLog> result = repository.findByInventoryId(200L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(200L, result.get(0).getInventoryId());
    }
}