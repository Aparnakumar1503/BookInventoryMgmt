package com.sprint.BookInventoryMgmt.authormgmt.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.BookInventoryMgmt.authormgmt.entity.Author;


public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
