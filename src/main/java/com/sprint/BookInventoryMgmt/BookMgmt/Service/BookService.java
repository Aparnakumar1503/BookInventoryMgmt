package com.sprint.BookInventoryMgmt.BookMgmt.Service;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;

import java.util.List;

public interface BookService {

    Book createBook(Book book);

    Book getBookByIsbn(String isbn);

    List<Book> getAllBooks();

    Book updateBook(String isbn, Book book);

    void deleteBook(String isbn);

    List<Book> getBooksByCategoryAndPublisher(Integer catId, Integer publisherId);
}