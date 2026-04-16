package com.sprint.BookInventoryMgmt.InventoryMgmt.Repository;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository repository;

    @Test
    void testSaveInventory() {
        Inventory inv = new Inventory();
        inv.setIsbn("1234567890123"); // ✅ FIX: Added ISBN
        inv.setRanks(1);
        inv.setPurchased(false);

        Inventory saved = repository.save(inv);

        assertNotNull(saved.getInventoryId());
    }

    @Test
    void testFindByIsbn() {
        Inventory inv = new Inventory();
        inv.setIsbn("9999999999999");
        inv.setRanks(1);
        inv.setPurchased(false);

        repository.save(inv);

        List<Inventory> result = repository.findByIsbn("9999999999999");

        assertFalse(result.isEmpty());
    }

    @Test
    void testFindByPurchasedFalse() {
        Inventory inv = new Inventory();
        inv.setIsbn("5555555555555");
        inv.setRanks(1);
        inv.setPurchased(false);

        repository.save(inv);

        List<Inventory> result = repository.findByPurchasedFalse();

        assertTrue(result.size() >= 1);
    }

    @Test
    void testFindByRanksAndPurchased() {
        Inventory inv1 = new Inventory();
        inv1.setIsbn("1111111111111");
        inv1.setRanks(1);
        inv1.setPurchased(false);

        Inventory inv2 = new Inventory();
        inv2.setIsbn("2222222222222");
        inv2.setRanks(1);
        inv2.setPurchased(true);

        repository.save(inv1);
        repository.save(inv2);

        List<Inventory> result = repository.findByRanksAndPurchased(1, false);

        assertEquals(1, result.size());
        assertFalse(result.get(0).getPurchased());
    }
}