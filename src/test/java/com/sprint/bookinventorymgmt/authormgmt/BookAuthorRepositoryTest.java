package com.sprint.bookinventorymgmt.authormgmt;
//
//import com.sprint.BookInventoryMgmt.AuthorMgmt.entity.*;
//import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.BookAuthorRepository;
//
//import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;
//import com.sprint.BookInventoryMgmt.BookMgmt.Repository.BookRepository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
class BookAuthorRepositoryTest {
}

//
//    @Autowired
//    private BookAuthorRepository repository;
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Test
//    void testSaveBookAuthor() {
//
//        // Create Book
//        Book book = new Book();
//        book.setIsbn("ISBN111");
//        book.setTitle("Test Book");
//        bookRepository.save(book);
//
//        // Create BookAuthor
//        com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthor ba = new BookAuthor();
//        ba.setIsbn("ISBN111");
//        ba.setAuthorId(1);
//        ba.setPrimaryAuthor("Y");
//
//        BookAuthor saved = repository.save(ba);
//
//        assertNotNull(saved);
//        assertEquals("ISBN111", saved.getIsbn());
//    }
//}