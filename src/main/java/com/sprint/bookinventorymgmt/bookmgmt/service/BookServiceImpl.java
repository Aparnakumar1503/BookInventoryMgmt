package com.sprint.bookinventorymgmt.bookmgmt.service;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.BookRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.dto.response.BookResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Book;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Category;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Publisher;
import com.sprint.bookinventorymgmt.bookmgmt.exception.*;
import com.sprint.bookinventorymgmt.bookmgmt.mapper.BookMapper;
import com.sprint.bookinventorymgmt.bookmgmt.repository.BookRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.CategoryRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.PublisherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    // ================= CREATE =================
    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {

        // 🔥 BUSINESS VALIDATION (NOT DTO)
        if (bookRepository.existsById(dto.getIsbn())) {
            throw new BookAlreadyExistsException(
                    "Book already exists with ISBN: " + dto.getIsbn()
            );
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with ID: " + dto.getCategoryId()));

        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() ->
                        new PublisherNotFoundException("Publisher not found with ID: " + dto.getPublisherId()));

        Book book = BookMapper.toEntity(dto, category, publisher);

        return BookMapper.toResponse(bookRepository.save(book));
    }

    // ================= GET BY ISBN =================
    @Override
    public BookResponseDTO getBookByIsbn(String isbn) {

        if (isbn == null || isbn.trim().isEmpty()) {
            throw new InvalidInputException("ISBN cannot be null or empty");
        }

        return bookRepository.findById(isbn)
                .map(BookMapper::toResponse)
                .orElseThrow(() ->
                        new BookNotFoundException("Book not found with ISBN: " + isbn));
    }

    // ================= GET ALL =================
    @Override
    public List<BookResponseDTO> getAllBooks() {

        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            throw new DataNotFoundException("No books available in the system");
        }

        return books.stream()
                .map(BookMapper::toResponse)
                .toList();
    }

    // ================= FILTER: CATEGORY =================
    @Override
    public List<BookResponseDTO> getBooksByCategory(Integer catId) {

        if (catId == null) {
            throw new InvalidInputException("Category ID cannot be null");
        }

        if (!categoryRepository.existsById(catId)) {
            throw new CategoryNotFoundException("Category not found with ID: " + catId);
        }

        List<Book> books = bookRepository.findByCategoryCatId(catId);

        if (books.isEmpty()) {
            throw new DataNotFoundException("No books found for category ID: " + catId);
        }

        return books.stream()
                .map(BookMapper::toResponse)
                .toList();
    }

    // ================= FILTER: PUBLISHER =================
    @Override
    public List<BookResponseDTO> getBooksByPublisher(Integer publisherId) {

        if (publisherId == null) {
            throw new InvalidInputException("Publisher ID cannot be null");
        }

        if (!publisherRepository.existsById(publisherId)) {
            throw new PublisherNotFoundException("Publisher not found with ID: " + publisherId);
        }

        List<Book> books = bookRepository.findByPublisherPublisherId(publisherId);

        if (books.isEmpty()) {
            throw new DataNotFoundException("No books found for publisher ID: " + publisherId);
        }

        return books.stream()
                .map(BookMapper::toResponse)
                .toList();
    }

    // ================= UPDATE =================
    @Override
    public BookResponseDTO updateBook(String isbn, BookRequestDTO dto) {

        if (isbn == null || isbn.trim().isEmpty()) {
            throw new InvalidInputException("ISBN cannot be null or empty");
        }

        Book existing = bookRepository.findById(isbn)
                .orElseThrow(() ->
                        new BookNotFoundException("Book not found with ISBN: " + isbn));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with ID: " + dto.getCategoryId()));

        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() ->
                        new PublisherNotFoundException("Publisher not found with ID: " + dto.getPublisherId()));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setEdition(dto.getEdition());
        existing.setCategory(category);
        existing.setPublisher(publisher);

        return BookMapper.toResponse(bookRepository.save(existing));
    }

    // ================= DELETE =================
    @Override
    public String deleteBook(String isbn) {

        if (isbn == null || isbn.trim().isEmpty()) {
            throw new InvalidInputException("ISBN cannot be null or empty");
        }

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() ->
                        new BookNotFoundException("Book not found with ISBN: " + isbn));

        bookRepository.delete(book);

        return "Book deleted successfully with ISBN: " + isbn;
    }

    // ================= FILTER: CATEGORY + PUBLISHER =================
    @Override
    public List<BookResponseDTO> getBooksByCategoryAndPublisher(Integer catId, Integer publisherId) {

        if (catId == null || publisherId == null) {
            throw new InvalidInputException("Category ID and Publisher ID cannot be null");
        }

        if (!categoryRepository.existsById(catId)) {
            throw new CategoryNotFoundException("Category not found with ID: " + catId);
        }

        if (!publisherRepository.existsById(publisherId)) {
            throw new PublisherNotFoundException("Publisher not found with ID: " + publisherId);
        }

        List<Book> books =
                bookRepository.findByCategoryCatIdAndPublisherPublisherId(catId, publisherId);

        if (books.isEmpty()) {
            throw new DataNotFoundException(
                    "No books found for category ID: " + catId +
                            " and publisher ID: " + publisherId
            );
        }

        return books.stream()
                .map(BookMapper::toResponse)
                .toList();
    }
}