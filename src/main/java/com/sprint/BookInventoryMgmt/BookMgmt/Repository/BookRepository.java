package com.sprint.BookInventoryMgmt.BookMgmt.Repository;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByCategoryCatIdAndPublisherPublisherId(Integer catId, Integer publisherId);
}