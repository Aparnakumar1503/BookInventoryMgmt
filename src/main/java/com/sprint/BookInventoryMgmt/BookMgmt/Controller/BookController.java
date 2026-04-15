package com.sprint.BookInventoryMgmt.BookMgmt.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    // ✅ Create Book
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // ✅ Get All Books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }



    }
