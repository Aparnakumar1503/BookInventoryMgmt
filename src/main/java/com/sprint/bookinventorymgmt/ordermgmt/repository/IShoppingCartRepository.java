

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

// Marks this interface as a Spring Data repository component
// WHY: Allows Spring to automatically create implementation at runtime and manage it as a bean
@Repository

// Extends JpaRepository to get built-in CRUD operations (save, findAll, delete, etc.)
// WHY: Eliminates need to write boilerplate DAO implementation manually
public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, ShoppingCartId> {

    // Derived query method using Spring naming convention
    // WHY: Automatically generates query to fetch cart items by userId without writing SQL/JPQL
    List<ShoppingCart> findByIdUserId(Integer userId);

    // Derived query method to fetch cart items by ISBN
    // WHY: Useful for checking which users added a specific book to cart
    List<ShoppingCart> findByIdIsbn(String isbn);

    // Custom JPQL query to fetch a specific cart item using composite key
    // WHY: Used when we need precise control over query with multiple conditions
    @Query("SELECT s FROM ShoppingCart s WHERE s.id.userId = :userId AND s.id.isbn = :isbn")
    ShoppingCart findByUserIdAndIsbn(
            @Param("userId") Integer userId,
            @Param("isbn") String isbn);

    // Custom JPQL query to count total items in a user's cart
    // WHY: Useful for cart summary, validation, or business rules like cart limit
    @Query("SELECT COUNT(s) FROM ShoppingCart s WHERE s.id.userId = :userId")
    Long countByUserId(@Param("userId") Integer userId);

    // Indicates that this query modifies data (DELETE operation)
    // WHY: Required because Spring treats SELECT as default; without this, delete will fail
    @Modifying

    // Ensures this delete operation runs inside a transaction
    // WHY: Guarantees data consistency during delete operation
    @Transactional

    // Custom delete query using composite key fields
    // WHY: Deletes specific cart item without loading full entity into memory (performance optimization)
    @Query("DELETE FROM ShoppingCart s WHERE s.id.userId = :userId AND s.id.isbn = :isbn")
    void deleteByUserIdAndIsbn(
            @Param("userId") Integer userId,
            @Param("isbn") String isbn);
}













































//    @Repository
//    public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, ShoppingCartId> {
//
//        // for repo tests
//        List<ShoppingCart> findByIdUserId(Integer userId);
//        List<ShoppingCart> findByIdIsbn(String isbn);
//
//        // Custom Query 1 — Find specific cart item by userId and isbn
//        @Query("SELECT s FROM ShoppingCart s WHERE s.id.userId = :userId AND s.id.isbn = :isbn")
//        ShoppingCart findByUserIdAndIsbn(@Param("userId") Integer userId, @Param("isbn") String isbn);
//
//        // Custom Query 2 — Count total items in a user's cart
//        @Query("SELECT COUNT(s) FROM ShoppingCart s WHERE s.id.userId = :userId")
//        Long countByUserId(@Param("userId") Integer userId);
//
//        // Custom Query 3 — Delete specific cart item by userId and isbn
//        @Modifying
//        @Transactional
//        @Query("DELETE FROM ShoppingCart s WHERE s.id.userId = :userId AND s.id.isbn = :isbn")
//        void deleteByUserIdAndIsbn(@Param("userId") Integer userId, @Param("isbn") String isbn);
//    }
//
//
//
//
