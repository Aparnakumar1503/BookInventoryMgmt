package com.sprint.bookinventorymgmt.bookmgmt.repository;

import com.sprint.bookinventorymgmt.bookmgmt.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {

    // 🔍 FILTERING
    List<Book> findByCategoryCatIdAndPublisherPublisherId(Integer catId, Integer publisherId);

    List<Book> findByCategoryCatId(Integer catId);

    List<Book> findByPublisherPublisherId(Integer publisherId);

    // 🔍 SEARCH / VALIDATION
    Optional<Book> findByIsbn(String isbn); // (optional, since ID is ISBN)

    boolean existsByTitle(String title); // prevent duplicate titles (optional rule)

    boolean existsByIsbn(String isbn); // 🔥 VERY IMPORTANT (used in service)

    // 🔍 SEARCH BY TITLE (useful feature)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // 🔍 COMBINED SEARCH (advanced filtering)
    List<Book> findByTitleContainingIgnoreCaseAndCategoryCatId(String title, Integer catId);

}