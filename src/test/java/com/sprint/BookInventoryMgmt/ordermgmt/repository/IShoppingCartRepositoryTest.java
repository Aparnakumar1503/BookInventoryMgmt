package com.sprint.BookInventoryMgmt.OrderMgmt.Repository;

import com.sprint.BookInventoryMgmt.ordermgmt.entity.ShoppingCart;
import com.sprint.BookInventoryMgmt.ordermgmt.entity.ShoppingCartId;
import com.sprint.BookInventoryMgmt.ordermgmt.repository.IShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IShoppingCartRepositoryTest {

    @Autowired
    private IShoppingCartRepository repository;

    @Test
    void testSaveShoppingCart() {
        ShoppingCart cart = new ShoppingCart(1, "1-111-11111-4");
        ShoppingCart saved = repository.saveAndFlush(cart);

        assertNotNull(saved);
        assertEquals(1, saved.getUserId());
        assertEquals("1-111-11111-4", saved.getIsbn());
    }

    @Test
    void testFindByUserId() {
        repository.saveAndFlush(new ShoppingCart(1, "1-111-11111-4"));
        repository.saveAndFlush(new ShoppingCart(1, "1-222-32443-7"));

        List<ShoppingCart> result = repository.findByIdUserId(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getUserId());
    }

    @Test
    void testFindByIsbn() {
        repository.saveAndFlush(new ShoppingCart(1, "1-111-11111-4"));
        repository.saveAndFlush(new ShoppingCart(2, "1-222-32443-7"));

        List<ShoppingCart> result = repository.findByIdIsbn("1-111-11111-4");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1-111-11111-4", result.get(0).getIsbn());
    }

    // Custom Query 1 — find by userId and isbn
    @Test
    void testFindByUserIdAndIsbn() {
        repository.saveAndFlush(new ShoppingCart(1, "1-111-11111-4"));

        ShoppingCart result = repository.findByUserIdAndIsbn(1, "1-111-11111-4");

        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals("1-111-11111-4", result.getIsbn());
    }

    // Custom Query 2 — count by userId
    @Test
    void testCountByUserId() {
        repository.saveAndFlush(new ShoppingCart(1, "1-111-11111-4"));
        repository.saveAndFlush(new ShoppingCart(1, "1-222-32443-7"));
        repository.saveAndFlush(new ShoppingCart(1, "1-295-84547-1"));

        Long count = repository.countByUserId(1);

        assertNotNull(count);
        assertEquals(3L, count);
    }

    // Custom Query 3 — delete by userId and isbn
    @Test
    void testDeleteByUserIdAndIsbn() {
        repository.saveAndFlush(new ShoppingCart(1, "1-111-11111-4"));
        repository.saveAndFlush(new ShoppingCart(1, "1-222-32443-7"));

        repository.deleteByUserIdAndIsbn(1, "1-111-11111-4");

        List<ShoppingCart> result = repository.findByIdUserId(1);

        assertEquals(1, result.size());                          // only 1 item remaining
        assertEquals("1-222-32443-7", result.get(0).getIsbn()); // correct item remains
    }
}