package com.sprint.BookInventoryMgmt.BookMgmt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;

public interface BookRepository extends JpaRepository<Book, String> {
}