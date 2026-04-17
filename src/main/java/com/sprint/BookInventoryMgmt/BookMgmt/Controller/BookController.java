package com.sprint.BookInventoryMgmt.BookMgmt.Controller;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;
import com.sprint.BookInventoryMgmt.BookMgmt.Service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/{isbn}")
    public Book get(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    // ✅ FIXED METHOD
    @GetMapping
    public List<Book> getAll(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer publisherId) {

        if (categoryId != null && publisherId != null) {
            return bookService.getBooksByCategoryAndPublisher(categoryId, publisherId);
        }

        return bookService.getAllBooks();
    }

    @PutMapping("/{isbn}")
    public Book update(@PathVariable String isbn, @RequestBody Book book) {
        return bookService.updateBook(isbn, book);
    }

    @DeleteMapping("/{isbn}")
    public void delete(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
    }
}