package com.sprint.BookInventoryMgmt.BookMgmt.Repository;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}