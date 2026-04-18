package com.sprint.BookInventoryMgmt.AuthorMgmt.Service;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.Author;

import java.util.List;

public interface AuthorService {

    Author createAuthor(Author author);

    Author getAuthorById(Integer id);

    List<Author> getAllAuthors();

    Author updateAuthor(Integer id, Author author);

    void deleteAuthor(Integer id);
}