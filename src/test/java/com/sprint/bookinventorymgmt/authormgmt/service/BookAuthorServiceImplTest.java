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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testAddBookAuthor_Positive() {
        when(repo.findByIsbn("1-111-11111-4")).thenReturn(Collections.emptyList());
        when(repo.save(any(BookAuthor.class))).thenReturn(bookAuthor);

        BookAuthorResponseDTO result = service.addBookAuthor(requestDTO);

        assertNotNull(result);
        assertEquals("1-111-11111-4", result.getIsbn());
        assertEquals(1, result.getAuthorId());
        verify(repo).save(any(BookAuthor.class));
    }

    @Test
    void testGetAllBookAuthors_Positive() {
        when(repo.findAll()).thenReturn(List.of(bookAuthor));

        List<BookAuthorResponseDTO> result = service.getAllBookAuthors();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    @Test
    void testGetByIsbn_Positive() {
        when(repo.findByIsbn("1-111-11111-4")).thenReturn(List.of(bookAuthor));

        List<BookAuthorResponseDTO> result = service.getByIsbn("1-111-11111-4");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1-111-11111-4", result.get(0).getIsbn());
        verify(repo).findByIsbn("1-111-11111-4");
    }

    @Test
    void testGetByAuthorId_Positive() {
        when(repo.findByAuthorId(1)).thenReturn(List.of(bookAuthor));

        List<BookAuthorResponseDTO> result = service.getByAuthorId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getAuthorId());
        verify(repo).findByAuthorId(1);
    }

    @Test
    void testGetPrimaryAuthorByIsbn_Positive() {
        when(repo.findPrimaryAuthorByIsbn("1-111-11111-4")).thenReturn(bookAuthor);

        BookAuthorResponseDTO result = service.getPrimaryAuthorByIsbn("1-111-11111-4");

        assertNotNull(result);
        assertEquals("Y", result.getPrimaryAuthor());
        verify(repo).findPrimaryAuthorByIsbn("1-111-11111-4");
    }

    @Test
    void testDeleteByIsbn_Positive() {
        when(repo.findByIsbn("1-111-11111-4")).thenReturn(List.of(bookAuthor));
        doNothing().when(repo).deleteByIsbn("1-111-11111-4");

        String result = service.deleteByIsbn("1-111-11111-4");

        assertEquals("BookAuthor entries deleted successfully for isbn: 1-111-11111-4", result);
        verify(repo).deleteByIsbn("1-111-11111-4");
    }

    @Test
    void testFindBooksByAuthorId_Positive() {
        when(repo.findBooksByAuthorId(1)).thenReturn(List.of(bookAuthor));

        List<BookAuthorResponseDTO> result = service.findBooksByAuthorId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repo).findBooksByAuthorId(1);
    }

    @Test
    void testGetAllBookAuthors_Negative_EmptyList() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        List<BookAuthorResponseDTO> result = service.getAllBookAuthors();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repo).findAll();
    }

    @Test
    void testGetByIsbn_Negative_NotFound() {
        when(repo.findByIsbn("INVALID")).thenReturn(Collections.emptyList());

        assertThrows(BookAuthorNotFoundException.class, () -> service.getByIsbn("INVALID"));
        verify(repo).findByIsbn("INVALID");
    }

    @Test
    void testGetByAuthorId_Negative_NotFound() {
        when(repo.findByAuthorId(99)).thenReturn(Collections.emptyList());

        assertThrows(BookAuthorNotFoundException.class, () -> service.getByAuthorId(99));
        verify(repo).findByAuthorId(99);
    }

    @Test
    void testGetPrimaryAuthorByIsbn_Negative_NotFound() {
        when(repo.findPrimaryAuthorByIsbn("INVALID")).thenReturn(null);

        assertThrows(BookAuthorNotFoundException.class, () -> service.getPrimaryAuthorByIsbn("INVALID"));
        verify(repo).findPrimaryAuthorByIsbn("INVALID");
    }

    @Test
    void testDeleteByIsbn_Negative_NotFound() {
        when(repo.findByIsbn("INVALID")).thenReturn(Collections.emptyList());

        assertThrows(BookAuthorNotFoundException.class, () -> service.deleteByIsbn("INVALID"));
        verify(repo).findByIsbn("INVALID");
    }

    @Test
    void testFindBooksByAuthorId_Negative_NotFound() {
        when(repo.findBooksByAuthorId(99)).thenReturn(Collections.emptyList());

        assertThrows(BookAuthorNotFoundException.class, () -> service.findBooksByAuthorId(99));
        verify(repo).findBooksByAuthorId(99);
    }

    @Test
    void testAddBookAuthor_Negative_NullIsbn() {
        BookAuthorRequestDTO invalidDTO = BookAuthorRequestDTO.builder()
                .isbn(null)
                .authorId(1)
                .primaryAuthor("Y")
                .build();

        assertThrows(InvalidBookDataException.class, () -> service.addBookAuthor(invalidDTO));
        verify(repo, never()).save(any(BookAuthor.class));
    }

    @Test
    void testFindBooksByAuthorId_Negative_NullAuthorId() {
        when(repo.findBooksByAuthorId(null)).thenReturn(Collections.emptyList());

        assertThrows(BookAuthorNotFoundException.class, () -> service.findBooksByAuthorId(null));
        verify(repo).findBooksByAuthorId(null);
    }

    @Test
    void testAddBookAuthor_Negative_DuplicateMapping() {
        when(repo.findByIsbn("1-111-11111-4")).thenReturn(List.of(bookAuthor));

        assertThrows(DuplicateBookAuthorException.class, () -> service.addBookAuthor(requestDTO));
        verify(repo, never()).save(any(BookAuthor.class));
    }
}
