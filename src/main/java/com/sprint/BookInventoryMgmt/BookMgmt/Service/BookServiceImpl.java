package com.sprint.BookInventoryMgmt.BookMgmt.Service;

import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.BookRepository;
import com.sprint.BookInventoryMgmt.BookMgmt.Exception.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ISBN: " + isbn));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(String isbn, Book book) {
        Book existing = getBookByIsbn(isbn);

        existing.setTitle(book.getTitle());
        existing.setDescription(book.getDescription());
        existing.setEdition(book.getEdition());
        existing.setCategory(book.getCategory());
        existing.setPublisher(book.getPublisher());

        return bookRepository.save(existing);
    }

    @Override
    public void deleteBook(String isbn) {
        Book existing = getBookByIsbn(isbn);
        bookRepository.delete(existing);
    }

    @Override
    public List<Book> getBooksByCategoryAndPublisher(Integer catId, Integer publisherId) {
        return bookRepository.findByCategoryCatIdAndPublisherPublisherId(catId, publisherId);
    }
}