

package com.sprint.bookinventorymgmt.ordermgmt.repository;

import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCart;
import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

    @Repository
    public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, ShoppingCartId> {

        // for repo tests
        List<ShoppingCart> findByIdUserId(Integer userId);
        List<ShoppingCart> findByIdIsbn(String isbn);

        // Custom Query 1 — Find specific cart item by userId and isbn
        @Query("SELECT s FROM ShoppingCart s WHERE s.id.userId = :userId AND s.id.isbn = :isbn")
        ShoppingCart findByUserIdAndIsbn(@Param("userId") Integer userId, @Param("isbn") String isbn);

        // Custom Query 2 — Count total items in a user's cart
        @Query("SELECT COUNT(s) FROM ShoppingCart s WHERE s.id.userId = :userId")
        Long countByUserId(@Param("userId") Integer userId);

        // Custom Query 3 — Delete specific cart item by userId and isbn
        @Modifying
        @Transactional
        @Query("DELETE FROM ShoppingCart s WHERE s.id.userId = :userId AND s.id.isbn = :isbn")
        void deleteByUserIdAndIsbn(@Param("userId") Integer userId, @Param("isbn") String isbn);
    }




