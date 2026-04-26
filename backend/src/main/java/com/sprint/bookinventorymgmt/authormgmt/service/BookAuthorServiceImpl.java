package com.sprint.bookinventorymgmt.authormgmt.service;
import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthor;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.BookAuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateBookAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.PrimaryAuthorConflictException;
import com.sprint.bookinventorymgmt.authormgmt.repository.BookAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookAuthorServiceImpl implements IBookAuthorService {

    @Autowired
    private  BookAuthorRepository repo;

    public BookAuthorServiceImpl(BookAuthorRepository repo) {
        this.repo = repo;
    }

    @Override
    public BookAuthorResponseDTO addBookAuthor(BookAuthorRequestDTO dto) {
        Objects.requireNonNull(dto, "Book-author request cannot be null");

        List<BookAuthor> existingBookAuthors = repo.findByIsbn(dto.getIsbn());
        if (isPrimaryAuthor(dto) && repo.findPrimaryAuthorByIsbn(dto.getIsbn()) != null) {
            throw new PrimaryAuthorConflictException(
                    "A primary author is already assigned for isbn: " + dto.getIsbn());
        }

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

        repo.save(entity);

        BookAuthor savedWithRelations = repo.findByIsbn(dto.getIsbn()).stream()
                .filter(bookAuthor -> dto.getAuthorId().equals(bookAuthor.getAuthorId()))
                .findFirst()
                .orElse(entity);

        return mapToDTO(savedWithRelations);
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

    private boolean isPrimaryAuthor(BookAuthorRequestDTO dto) {
        return dto.getPrimaryAuthor() != null && "Y".equalsIgnoreCase(dto.getPrimaryAuthor().trim());
    }

}
