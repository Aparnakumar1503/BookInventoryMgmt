package com.sprint.bookinventorymgmt.authormgmt.repository;

import com.sprint.bookinventorymgmt.authormgmt.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findByLastName(String lastName);
    List<Author> findByFirstName(String firstName);
    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndAuthorIdNot(String firstName, String lastName, Integer authorId);

    @Query("SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName")
    Author findByFirstNameAndLastName(@Param("firstName") String firstName,
                                      @Param("lastName") String lastName);
}
