package com.sprint.bookinventorymgmt.inventorymgmt.repository;

import com.sprint.bookinventorymgmt.inventorymgmt.entity.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class InventoryRepositoryTest {

    @Autowired
    private IInventoryRepository repository;

    @Test
    void isbnQueries_returnAllAndAvailableInventory() {
        repository.save(new Inventory(null, "9999999999999", 1, false));
        repository.save(new Inventory(null, "9999999999999", 2, true));

        assertEquals(2, repository.findByIsbn("9999999999999").size());
        assertEquals(1, repository.findByIsbnAndPurchasedFalse("9999999999999").size());
    }

    @Test
    void statusAndRankQueries_returnExpectedInventory() {
        repository.save(new Inventory(null, "5555555555555", 1, false));
        repository.save(new Inventory(null, "6666666666666", 1, false));

        assertFalse(repository.findByPurchasedFalse().isEmpty());
        assertEquals(2, repository.findByRanksAndPurchased(1, false).size());
    }
}
