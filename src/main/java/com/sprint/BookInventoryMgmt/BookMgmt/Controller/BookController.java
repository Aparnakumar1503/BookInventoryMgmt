package com.sprint.BookInventoryMgmt.BookMgmt.Controller;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // CREATE
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book saved = bookRepository.save(book);
        return ResponseEntity.status(201).body(saved);
    }

    // GET ALL
    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer publisherId
    ) {
        if (categoryId != null && publisherId != null) {
            return bookRepository.findByCategoryCatIdAndPublisherPublisherId(categoryId, publisherId);
        }
        return bookRepository.findAll();
    }

    // GET BY ID
    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable String isbn) {
        Optional<Book> book = bookRepository.findById(isbn);
        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable String isbn,
                                           @RequestBody Book updatedBook) {

        return bookRepository.findById(isbn)
                .map(existing -> {
                    existing.setTitle(updatedBook.getTitle());
                    existing.setDescription(updatedBook.getDescription());
                    existing.setEdition(updatedBook.getEdition());
                    existing.setCategory(updatedBook.getCategory());
                    existing.setPublisher(updatedBook.getPublisher());

                    return ResponseEntity.ok(bookRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        if (bookRepository.existsById(isbn)) {
            bookRepository.deleteById(isbn);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}