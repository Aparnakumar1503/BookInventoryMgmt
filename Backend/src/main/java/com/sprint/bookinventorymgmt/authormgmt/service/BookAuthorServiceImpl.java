package com.sprint.bookinventorymgmt.authormgmt.service;
import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthor;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.BookAuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateBookAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.InvalidBookDataException;
import com.sprint.bookinventorymgmt.authormgmt.repository.BookAuthorRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookAuthorServiceImpl implements IBookAuthorService {


    private final BookAuthorRepository repo;

    public BookAuthorServiceImpl(BookAuthorRepository repo) {
        this.repo = repo;
    }

    @Override
    public BookAuthorResponseDTO addBookAuthor(BookAuthorRequestDTO dto) {
        validateBookAuthorRequest(dto);

        List<BookAuthor> existingBookAuthors = repo.findByIsbn(dto.getIsbn());
        boolean alreadyMapped = false;
        for (BookAuthor bookAuthor : existingBookAuthors) {
            if (dto.getAuthorId().equals(bookAuthor.getAuthorId())) {
                alreadyMapped = true;
                break;
            }
        }

        if (alreadyMapped) {
            throw new DuplicateBookAuthorException(
                    "Book author mapping already exists for isbn: " + dto.getIsbn() + " and authorId: " + dto.getAuthorId());
        }

        BookAuthor entity = new BookAuthor();
        entity.setIsbn(dto.getIsbn());
        entity.setAuthorId(dto.getAuthorId());
        entity.setPrimaryAuthor(dto.getPrimaryAuthor());

        BookAuthor saved = repo.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public List<BookAuthorResponseDTO> getAllBookAuthors() {
        List<BookAuthor> bookAuthors = repo.findAll();
        List<BookAuthorResponseDTO> response = new ArrayList<>();

        for (BookAuthor bookAuthor : bookAuthors) {
            response.add(mapToDTO(bookAuthor));
        }

        return response;
    }

    @Override
    public List<BookAuthorResponseDTO> getByIsbn(String isbn) {
        List<BookAuthor> list = repo.findByIsbn(isbn);
        if (list.isEmpty()) {
            throw new BookAuthorNotFoundException("No authors found for isbn: " + isbn);
        }

        List<BookAuthorResponseDTO> response = new ArrayList<>();
        for (BookAuthor bookAuthor : list) {
            response.add(mapToDTO(bookAuthor));
        }

        return response;
    }

    @Override
    public List<BookAuthorResponseDTO> getByAuthorId(Integer authorId) {
        List<BookAuthor> list = repo.findByAuthorId(authorId);
        if (list.isEmpty()) {
            throw new BookAuthorNotFoundException("No books found for authorId: " + authorId);
        }

        List<BookAuthorResponseDTO> response = new ArrayList<>();
        for (BookAuthor bookAuthor : list) {
            response.add(mapToDTO(bookAuthor));
        }

        return response;
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
    public String deleteByIsbnAndAuthorId(String isbn, Integer authorId) {
        int deleted = repo.deleteByIsbnAndAuthorId(isbn, authorId);
        if (deleted == 0) {
            throw new BookAuthorNotFoundException(
                    "No mapping found for isbn: " + isbn + " and authorId: " + authorId);
        }
        return "BookAuthor entry deleted successfully for isbn: " + isbn + " and authorId: " + authorId;
    }

    @Override
    public List<BookAuthorResponseDTO> findBooksByAuthorId(Integer authorId) {
        List<BookAuthor> list = repo.findBooksByAuthorId(authorId);
        if (list.isEmpty()) {
            throw new BookAuthorNotFoundException("No books found for authorId: " + authorId);
        }

        List<BookAuthorResponseDTO> response = new ArrayList<>();
        for (BookAuthor bookAuthor : list) {
            response.add(mapToDTO(bookAuthor));
        }

        return response;
    }

    private BookAuthorResponseDTO mapToDTO(BookAuthor entity) {
        BookAuthorResponseDTO dto = new BookAuthorResponseDTO();
        dto.setIsbn(entity.getIsbn());
        dto.setAuthorId(entity.getAuthorId());
        dto.setPrimaryAuthor(entity.getPrimaryAuthor());
        dto.setFirstName(entity.getAuthor() != null ? entity.getAuthor().getFirstName() : null);
        dto.setLastName(entity.getAuthor() != null ? entity.getAuthor().getLastName() : null);
        dto.setBookTitle(entity.getBook() != null ? entity.getBook().getTitle() : null);
        return dto;
    }

    private void validateBookAuthorRequest(BookAuthorRequestDTO dto) {
        if (dto == null
                || dto.getIsbn() == null || dto.getIsbn().isBlank()
                || dto.getAuthorId() == null
                || dto.getPrimaryAuthor() == null || dto.getPrimaryAuthor().isBlank()) {
            throw new InvalidBookDataException("ISBN, authorId and primaryAuthor cannot be null or empty");
        }
    }
}
