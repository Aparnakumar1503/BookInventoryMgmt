package com.sprint.bookinventorymgmt.ordermgmt.repository;

import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class IShoppingCartRepositoryTest {

    @Autowired
    private IShoppingCartRepository repository;

    @Test
    void derivedQueries_findByUserId_andIsbn_returnMatches() {
        repository.saveAndFlush(new ShoppingCart(1, "1-111-11111-4"));
        repository.saveAndFlush(new ShoppingCart(1, "1-222-32443-7"));

        assertEquals(2, repository.findByIdUserId(1).size());
        assertEquals(1, repository.findByIdIsbn("1-111-11111-4").size());
    }

    @Test
    void customQuery_findByUserIdAndIsbn_returnsSpecificItem() {
        repository.saveAndFlush(new ShoppingCart(1, "1-111-11111-4"));

        assertNotNull(repository.findByUserIdAndIsbn(1, "1-111-11111-4"));
    }

    @Test
    void customQuery_countByUserId_returnsTotalItemsForUser() {
        repository.saveAndFlush(new ShoppingCart(1, "1-111-11111-4"));
        repository.saveAndFlush(new ShoppingCart(1, "1-222-32443-7"));

        assertEquals(2L, repository.countByUserId(1));
    }

    @Test
    void customQuery_deleteByUserIdAndIsbn_removesSpecificItem() {
        repository.saveAndFlush(new ShoppingCart(1, "1-111-11111-4"));
        repository.saveAndFlush(new ShoppingCart(1, "1-222-32443-7"));

        repository.deleteByUserIdAndIsbn(1, "1-111-11111-4");

        assertEquals(1, repository.findByIdUserId(1).size());
    }
}
