package com.sprint.BookInventoryMgmt.AuthorMgmt.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.Author;


public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
