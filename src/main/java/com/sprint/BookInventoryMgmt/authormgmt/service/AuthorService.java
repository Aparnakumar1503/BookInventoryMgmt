package com.sprint.BookInventoryMgmt.authormgmt.service;

import com.sprint.BookInventoryMgmt.authormgmt.entity.Author;

import java.util.List;

public interface AuthorService {

    Author createAuthor(Author author);

    Author getAuthorById(Integer id);

    List<Author> getAllAuthors();

    Author updateAuthor(Integer id, Author author);

    void deleteAuthor(Integer id);
}