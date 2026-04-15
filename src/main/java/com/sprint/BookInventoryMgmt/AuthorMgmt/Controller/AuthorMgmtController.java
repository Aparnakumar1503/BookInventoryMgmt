package com.sprint.BookInventoryMgmt.AuthorMgmt.Controller;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.Author;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthor;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.AuthorRepository;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.BookAuthorRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthorMgmtController {


    private final AuthorRepository authorRepo;
    private final BookAuthorRepository bookAuthorRepo;

    public AuthorMgmtController(AuthorRepository authorRepo,
                                BookAuthorRepository bookAuthorRepo) {
        this.authorRepo = authorRepo;
        this.bookAuthorRepo = bookAuthorRepo;
    }

    @PostMapping("/authors")
    public Author addAuthor(@RequestBody Author author) {
        return authorRepo.save(author);
    }

    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    @PostMapping("/book-authors")
    public BookAuthor addMapping(@RequestBody BookAuthor ba) {
        return bookAuthorRepo.save(ba);
    }

    @GetMapping("/book-authors")
    public List<BookAuthor> getAllMappings() {
        return bookAuthorRepo.findAll();
    }
}

