package com.sprint.bookinventorymgmt.ordermgmt.repository;

import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class IPurchaseLogRepositoryTest {

    @Autowired
    private IPurchaseLogRepository repository;

    @Test
    void derivedQueries_findByUserId_andInventoryId_returnMatches() {
        repository.saveAndFlush(new PurchaseLog(1, 1000000));
        repository.saveAndFlush(new PurchaseLog(1, 1000001));

        assertEquals(2, repository.findByIdUserId(1).size());
        assertEquals(1, repository.findByIdInventoryId(1000000).size());
    }

    @Test
    void customQuery_findByUserIdAndInventoryId_returnsSpecificPurchase() {
        repository.saveAndFlush(new PurchaseLog(1, 1000000));

        assertNotNull(repository.findByUserIdAndInventoryId(1, 1000000));
    }

    @Test
    void customQuery_countByUserId_returnsTotalPurchasesForUser() {
        repository.saveAndFlush(new PurchaseLog(1, 1000000));
        repository.saveAndFlush(new PurchaseLog(1, 1000001));

        assertEquals(2L, repository.countByUserId(1));
    }

    @Test
    void customQuery_deleteByUserIdAndInventoryId_removesSpecificPurchase() {
        repository.saveAndFlush(new PurchaseLog(1, 1000000));
        repository.saveAndFlush(new PurchaseLog(1, 1000001));

        repository.deleteByUserIdAndInventoryId(1, 1000000);

        assertEquals(1, repository.findByIdUserId(1).size());
    }
}
