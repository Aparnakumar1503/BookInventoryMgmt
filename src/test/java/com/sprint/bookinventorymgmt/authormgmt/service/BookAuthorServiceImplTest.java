package com.sprint.bookinventorymgmt.authormgmt.service;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthor;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.BookAuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateBookAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.InvalidBookDataException;
import com.sprint.bookinventorymgmt.authormgmt.repository.BookAuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookAuthorServiceImplTest {

    @Mock
    private BookAuthorRepository repo;

    @InjectMocks
    private BookAuthorServiceImpl service;

    private BookAuthor bookAuthor;
    private BookAuthorRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookAuthor = new BookAuthor("1-111-11111-4", 1, null, null, "Y");
        requestDTO = BookAuthorRequestDTO.builder()
                .isbn("1-111-11111-4")
                .authorId(1)
                .primaryAuthor("Y")
                .build();
    }

    @Test
    void addBookAuthor_success() {
        when(repo.findByIsbn("1-111-11111-4")).thenReturn(Collections.emptyList());
        when(repo.save(any(BookAuthor.class))).thenReturn(bookAuthor);

        BookAuthorResponseDTO result = service.addBookAuthor(requestDTO);

        assertNotNull(result);
        assertEquals("1-111-11111-4", result.getIsbn());
        verify(repo).save(any(BookAuthor.class));
    }

    @Test
    void addBookAuthor_duplicate() {
        when(repo.findByIsbn("1-111-11111-4")).thenReturn(List.of(bookAuthor));

        assertThrows(DuplicateBookAuthorException.class, () -> service.addBookAuthor(requestDTO));
        verify(repo, never()).save(any(BookAuthor.class));
    }

    @Test
    void addBookAuthor_invalidRequest() {
        BookAuthorRequestDTO invalidDTO = BookAuthorRequestDTO.builder()
                .isbn(null)
                .authorId(1)
                .primaryAuthor("Y")
                .build();

        assertThrows(InvalidBookDataException.class, () -> service.addBookAuthor(invalidDTO));
    }

    @Test
    void getAllBookAuthors_success() {
        when(repo.findAll()).thenReturn(List.of(bookAuthor));

        List<BookAuthorResponseDTO> result = service.getAllBookAuthors();

        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    @Test
    void getByIsbn_notFound() {
        when(repo.findByIsbn("INVALID")).thenReturn(Collections.emptyList());

        assertThrows(BookAuthorNotFoundException.class, () -> service.getByIsbn("INVALID"));
    }

    @Test
    void getPrimaryAuthorByIsbn_success() {
        when(repo.findPrimaryAuthorByIsbn("1-111-11111-4")).thenReturn(bookAuthor);

        BookAuthorResponseDTO result = service.getPrimaryAuthorByIsbn("1-111-11111-4");

        assertEquals("Y", result.getPrimaryAuthor());
        verify(repo).findPrimaryAuthorByIsbn("1-111-11111-4");
    }

    @Test
    void deleteByIsbn_success() {
        when(repo.findByIsbn("1-111-11111-4")).thenReturn(List.of(bookAuthor));

        String result = service.deleteByIsbn("1-111-11111-4");

        assertNotNull(result);
        verify(repo).deleteByIsbn("1-111-11111-4");
    }

    @Test
    void findBooksByAuthorId_notFound() {
        when(repo.findBooksByAuthorId(99)).thenReturn(Collections.emptyList());

        assertThrows(BookAuthorNotFoundException.class, () -> service.findBooksByAuthorId(99));
    }
}
