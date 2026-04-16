package com.sprint.BookInventoryMgmt.InventoryMgmt.Repository;

import com.sprint.BookInventoryMgmt.InventoryMgmt.Entity.BookCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookConditionRepositoryTest {

    @Autowired
    private BookConditionRepository repository;

    @Test
    void testSaveBookCondition() {
        BookCondition bc = new BookCondition();
        bc.setRanks(1);
        bc.setDescription("Good");
        bc.setFullDescription("Good condition book");
        bc.setPrice(150.0);

        BookCondition saved = repository.save(bc);

        assertNotNull(saved);
        assertEquals("Good", saved.getDescription());
    }

    @Test
    void testFindAll() {
        BookCondition bc = new BookCondition();
        bc.setRanks(2);
        bc.setDescription("Excellent");
        bc.setFullDescription("Excellent condition book");
        bc.setPrice(250.0);

        repository.save(bc);

        List<BookCondition> list = repository.findAll();

        assertFalse(list.isEmpty());
    }

    @Test
    void testFindByPriceRange() {
        BookCondition bc1 = new BookCondition();
        bc1.setRanks(1);
        bc1.setDescription("Good");
        bc1.setFullDescription("Good condition book");
        bc1.setPrice(100.0);

        BookCondition bc2 = new BookCondition();
        bc2.setRanks(2);
        bc2.setDescription("Better");
        bc2.setFullDescription("Better condition book");
        bc2.setPrice(300.0);

        repository.save(bc1);
        repository.save(bc2);

        List<BookCondition> result = repository.findByPriceBetween(50.0, 200.0);

        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getPrice());
    }

    @Test
    void testFindByRankGreaterThan() {
        BookCondition bc1 = new BookCondition();
        bc1.setRanks(1);
        bc1.setDescription("Low");
        bc1.setFullDescription("Low condition");
        bc1.setPrice(100.0);

        BookCondition bc2 = new BookCondition();
        bc2.setRanks(5);
        bc2.setDescription("High");
        bc2.setFullDescription("High condition");
        bc2.setPrice(500.0);

        repository.save(bc1);
        repository.save(bc2);

        List<BookCondition> result = repository.findByRanksGreaterThan(2);

        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getRanks());
    }
}