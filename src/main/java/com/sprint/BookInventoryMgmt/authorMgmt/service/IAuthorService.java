package com.sprint.BookInventoryMgmt.authorMgmt.service;

import com.sprint.BookInventoryMgmt.authorMgmt.entity.Author;

import java.util.List;

public interface IAuthorService {

    Author createAuthor(Author author);

    Author getAuthorById(Integer id);

    List<Author> getAllAuthors();

    Author updateAuthor(Integer id, Author author);

    void deleteAuthor(Integer id);
}