package com.sprint.BookInventoryMgmt.OrderMgmt.Repository;

import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository repository;

    @Test
    void testSaveShoppingCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(1L);
        cart.setIsbn("ISBN123");


        ShoppingCart saved = repository.save(cart);

        assertNotNull(saved);
        assertEquals("ISBN123", saved.getIsbn());
    }

    @Test
    void testFindByUserId() {
        ShoppingCart cart1 = new ShoppingCart();
        cart1.setUserId(1L);
        cart1.setIsbn("ISBN111");


        ShoppingCart cart2 = new ShoppingCart();
        cart2.setUserId(2L);
        cart2.setIsbn("ISBN222");


        repository.save(cart1);
        repository.save(cart2);

        List<ShoppingCart> result = repository.findByUserId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
    }

    @Test
    void testFindByIsbn() {
        ShoppingCart cart1 = new ShoppingCart();
        cart1.setUserId(1L);
        cart1.setIsbn("ISBN999");


        ShoppingCart cart2 = new ShoppingCart();
        cart2.setUserId(2L);
        cart2.setIsbn("ISBN888");


        repository.save(cart1);
        repository.save(cart2);

        List<ShoppingCart> result = repository.findByIsbn("ISBN999");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("ISBN999", result.get(0).getIsbn());
    }

    @Test
    void testFindByUserIdAndIsbn() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(1L);
        cart.setIsbn("ISBN777");


        repository.save(cart);

        ShoppingCart result = repository.findByUserIdAndIsbn(1L, "ISBN777");

        assertNotNull(result);

    }
}