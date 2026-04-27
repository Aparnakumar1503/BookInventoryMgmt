package com.sprint.bookinventorymgmt.authormgmt.service;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthor;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.BookAuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateBookAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.repository.BookAuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
        requestDTO = new BookAuthorRequestDTO("1-111-11111-4", 1, "Y");
    }

    @Test
    void addBookAuthor_savesNewMapping_whenValid() {
        when(repo.findByIsbn("1-111-11111-4")).thenReturn(Collections.emptyList());
        when(repo.save(any(BookAuthor.class))).thenReturn(bookAuthor);

        BookAuthorResponseDTO result = service.addBookAuthor(requestDTO);

        assertEquals("1-111-11111-4", result.getIsbn());
        assertEquals(1, result.getAuthorId());
    }

    @Test
    void lookupOperations_returnExistingMappings() {
        when(repo.findAll()).thenReturn(List.of(bookAuthor));
        when(repo.findBooksByAuthorId(1)).thenReturn(List.of(bookAuthor));

        assertEquals(1, service.getAllBookAuthors().size());
        assertEquals(1, service.findBooksByAuthorId(1).size());
    }

    @Test
    void deleteOperations_removeMappings_whenRowsExist() {
        when(repo.deleteByIsbnAndAuthorId("1-111-11111-4", 1)).thenReturn(1);

        assertEquals(
                "BookAuthor entry deleted successfully for isbn: 1-111-11111-4 and authorId: 1",
                service.deleteByIsbnAndAuthorId("1-111-11111-4", 1)
        );
    }

    @Test
    void addBookAuthor_throwsForInvalidRequestOrDuplicateMapping() {
        when(repo.findByIsbn("1-111-11111-4")).thenReturn(List.of(bookAuthor));

        assertThrows(NullPointerException.class, () -> service.addBookAuthor(null));
        assertThrows(DuplicateBookAuthorException.class, () -> service.addBookAuthor(requestDTO));
    }

    void authorBasedLookups_throwWhenNoMappingsExist() {
        when(repo.findBooksByAuthorId(99)).thenReturn(Collections.emptyList());

        assertThrows(BookAuthorNotFoundException.class, () -> service.findBooksByAuthorId(99));
    }

    @Test
    void deleteByIsbnAndAuthorId_throwsWhenNothingIsDeleted_andGetAllHandlesEmptyList() {
        when(repo.deleteByIsbnAndAuthorId("missing", 99)).thenReturn(0);
        when(repo.findAll()).thenReturn(Collections.emptyList());

        assertThrows(BookAuthorNotFoundException.class, () -> service.deleteByIsbnAndAuthorId("missing", 99));
        assertTrue(service.getAllBookAuthors().isEmpty());
    }
}
