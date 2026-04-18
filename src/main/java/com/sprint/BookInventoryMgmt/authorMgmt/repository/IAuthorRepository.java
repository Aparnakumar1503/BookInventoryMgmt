package com.sprint.BookInventoryMgmt.authorMgmt.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.BookInventoryMgmt.authorMgmt.entity.Author;


public interface IAuthorRepository extends JpaRepository<Author, Integer> {
}
