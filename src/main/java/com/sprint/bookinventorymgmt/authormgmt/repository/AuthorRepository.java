package com.sprint.BookInventoryMgmt.authormgmt.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.BookInventoryMgmt.authormgmt.entity.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findByLastName(String lastName);
    List<Author> findByFirstName(String firstName);

    // Custom Query 1 — Find author by first and last name
    @Query("SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName")
    Author findByFirstNameAndLastName(@Param("firstName") String firstName,
                                      @Param("lastName") String lastName);

    // Custom Query 2 — Find authors whose last name contains a keyword
    @Query("SELECT a FROM Author a WHERE LOWER(a.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Author> searchByLastName(@Param("keyword") String keyword);

    // Custom Query 3 — Count total authors
    @Query("SELECT COUNT(a) FROM Author a")
    Long countAllAuthors();
}
