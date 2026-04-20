package com.sprint.bookinventorymgmt.inventorymgmt.repository;

import com.sprint.bookinventorymgmt.inventorymgmt.entity.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
class InventoryRepositoryTest {

    @Autowired
    private IInventoryRepository repository;

    @Test
    void testSaveInventory() {
        Inventory inv = new Inventory();
        inv.setIsbn("1234567890123");
        inv.setRanks(1);
        inv.setPurchased(false);

        Inventory saved = repository.save(inv);

        assertNotNull(saved.getInventoryId());
        assertEquals("1234567890123", saved.getIsbn());
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
}