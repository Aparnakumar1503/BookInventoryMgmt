package com.sprint.BookInventoryMgmt.orderMgmt.repository;


import com.sprint.BookInventoryMgmt.orderMgmt.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {


    List<ShoppingCart> findByUserId(Long userId);

    List<ShoppingCart> findByIsbn(String isbn);

    ShoppingCart findByUserIdAndIsbn(Long userId, String isbn);
}