package com.sprint.BookInventoryMgmt.BookMgmt.Service;

import com.sprint.BookInventoryMgmt.BookMgmt.DTO.requestdto.BookRequestDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.DTO.responsedto.BookResponseDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Category;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Publisher;
import com.sprint.BookInventoryMgmt.BookMgmt.Exception.BookNotFoundException;
import com.sprint.BookInventoryMgmt.BookMgmt.Exception.CategoryNotFoundException;
import com.sprint.BookInventoryMgmt.BookMgmt.Exception.PublisherNotFoundException;
import com.sprint.BookInventoryMgmt.BookMgmt.Mapper.BookMapper;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.BookRepository;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.CategoryRepository;
import com.sprint.BookInventoryMgmt.BookMgmt.Repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           CategoryRepository categoryRepository,
                           PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new PublisherNotFoundException("Publisher not found"));

        Book book = BookMapper.toEntity(dto, category, publisher);
        Book saved = bookRepository.save(book);

        return BookMapper.toResponse(saved);
    }

    @Override
    public BookResponseDTO getBookByIsbn(String isbn) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        return BookMapper.toResponse(book);
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO updateBook(String isbn, BookRequestDTO dto) {

        Book existing = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new PublisherNotFoundException("Publisher not found"));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setEdition(dto.getEdition());
        existing.setCategory(category);
        existing.setPublisher(publisher);

        Book updated = bookRepository.save(existing);

        return BookMapper.toResponse(updated);
    }

    @Override
    public void deleteBook(String isbn) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        bookRepository.delete(book);
    }

    @Override
    public List<BookResponseDTO> getBooksByCategoryAndPublisher(Integer catId, Integer publisherId) {
        return bookRepository.findByCategoryCatIdAndPublisherPublisherId(catId, publisherId)
                .stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }
}