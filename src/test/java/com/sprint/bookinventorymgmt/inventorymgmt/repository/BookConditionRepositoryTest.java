package com.sprint.bookinventorymgmt.inventorymgmt.repository;

import com.sprint.bookinventorymgmt.inventorymgmt.entity.BookCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BookConditionRepositoryTest {

    @Autowired
    private IBookConditionRepository repository;

    @Test
    void findByPriceBetween_returnsConditionsWithinRange() {
        repository.save(new BookCondition(1, "Good", "Good", 100.0));
        repository.save(new BookCondition(2, "Better", "Better", 300.0));

        assertEquals(1, repository.findByPriceBetween(50.0, 200.0).size());
    }

    @Test
    void findByRanksGreaterThan_returnsHigherRanks() {
        repository.save(new BookCondition(1, "Low", "Low", 100.0));
        repository.save(new BookCondition(5, "High", "High", 500.0));

        assertEquals(1, repository.findByRanksGreaterThan(2).size());
    }

    @Test
    void getBookConditionsAbovePrice_returnsCustomQueryMatches() {
        repository.save(new BookCondition(1, "Good", "Good", 100.0));
        repository.save(new BookCondition(2, "Premium", "Premium", 400.0));

        assertEquals(1, repository.getBookConditionsAbovePrice(250.0).size());
    }
}
