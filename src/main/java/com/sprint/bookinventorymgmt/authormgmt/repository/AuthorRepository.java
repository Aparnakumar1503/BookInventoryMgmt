package com.sprint.bookinventorymgmt.authormgmt.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.bookinventorymgmt.authormgmt.entity.Author;


public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
