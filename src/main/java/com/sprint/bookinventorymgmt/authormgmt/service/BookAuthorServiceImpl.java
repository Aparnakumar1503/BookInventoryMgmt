package com.sprint.bookinventorymgmt.authormgmt.service;
import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthor;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.BookAuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.repository.BookAuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookAuthorServiceImpl implements IBookAuthorService {


    private final BookAuthorRepository repo;

    public BookAuthorServiceImpl(BookAuthorRepository repo) {
        this.repo = repo;
    }

    @Override
    public BookAuthorResponseDTO addBookAuthor(BookAuthorRequestDTO dto) {
        BookAuthor entity = new BookAuthor();
        entity.setIsbn(dto.getIsbn());
        entity.setAuthorId(dto.getAuthorId());
        entity.setPrimaryAuthor(dto.getPrimaryAuthor());

        BookAuthor saved = repo.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public List<BookAuthorResponseDTO> getAllBookAuthors() {
        return repo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<BookAuthorResponseDTO> getByIsbn(String isbn) {
        List<BookAuthor> list = repo.findByIsbn(isbn);
        if (list.isEmpty()) {
            throw new BookAuthorNotFoundException("No authors found for isbn: " + isbn);
        }
        return list.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<BookAuthorResponseDTO> getByAuthorId(Integer authorId) {
        List<BookAuthor> list = repo.findByAuthorId(authorId);
        if (list.isEmpty()) {
            throw new BookAuthorNotFoundException("No books found for authorId: " + authorId);
        }
        return list.stream().map(this::mapToDTO).toList();
    }

    @Override
    public BookAuthorResponseDTO getPrimaryAuthorByIsbn(String isbn) {
        BookAuthor bookAuthor = repo.findPrimaryAuthorByIsbn(isbn);
        if (bookAuthor == null) {
            throw new BookAuthorNotFoundException("No primary author found for isbn: " + isbn);
        }
        return mapToDTO(bookAuthor);
    }

    @Override
    public String deleteByIsbn(String isbn) {
        List<BookAuthor> list = repo.findByIsbn(isbn);
        if (list.isEmpty()) {
            throw new BookAuthorNotFoundException("No entries found for isbn: " + isbn);
        }
        repo.deleteByIsbn(isbn);
        return "BookAuthor entries deleted successfully for isbn: " + isbn;
    }

    @Override
    public List<BookAuthorResponseDTO> findBooksByAuthorId(Integer authorId) {
        List<BookAuthor> list = repo.findBooksByAuthorId(authorId);
        if (list.isEmpty()) {
            throw new BookAuthorNotFoundException("No books found for authorId: " + authorId);
        }
        return list.stream().map(this::mapToDTO).toList();
    }

    private BookAuthorResponseDTO mapToDTO(BookAuthor entity) {
        return BookAuthorResponseDTO.builder()
                .isbn(entity.getIsbn())
                .authorId(entity.getAuthorId())
                .primaryAuthor(entity.getPrimaryAuthor())
                .firstName(entity.getAuthor() != null ? entity.getAuthor().getFirstName() : null)
                .lastName(entity.getAuthor() != null ? entity.getAuthor().getLastName() : null)
                .bookTitle(entity.getBook() != null ? entity.getBook().getTitle() : null)
                .build();
    }
}