package com.sprint.BookInventoryMgmt.OrderMgmt.Repository;


import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {














    List<ShoppingCart> findByUserId(Long userId);

    List<ShoppingCart> findByIsbn(String isbn);

    ShoppingCart findByUserIdAndIsbn(Long userId, String isbn);
}