package com.sprint.bookinventorymgmt.authormgmt.service;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.entity.Author;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.AuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository repo;

    @InjectMocks
    private AuthorServiceImpl service;

    private Author author;
    private AuthorRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author(1, "John", "Doe", "photo.jpg");
        requestDTO = AuthorRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .photo("photo.jpg")
                .build();
    }

    @Test
    void addAuthor_createsAuthor_whenRequestIsValid() {
        when(repo.findByFirstNameAndLastName("John", "Doe")).thenReturn(null);
        when(repo.save(any(Author.class))).thenReturn(author);

        AuthorResponseDTO result = service.addAuthor(requestDTO);

        assertEquals("John", result.getFirstName());
        verify(repo).save(any(Author.class));
    }

    @Test
    void authorReadOperations_returnMappedData() {
        when(repo.findAll()).thenReturn(List.of(author));
        when(repo.findById(1)).thenReturn(Optional.of(author));

        assertEquals(1, service.getAllAuthors().size());
        assertEquals(1, service.getAuthorById(1).getAuthorId());
    }

    @Test
    void updateAuthor_updatesExistingAuthor() {
        when(repo.findById(1)).thenReturn(Optional.of(author));
        when(repo.existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndAuthorIdNot("Jane", "Doe", 1)).thenReturn(false);
        when(repo.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AuthorRequestDTO updateDTO = AuthorRequestDTO.builder()
                .firstName("Jane")
                .lastName("Doe")
                .photo("updated.jpg")
                .build();

        AuthorResponseDTO result = service.updateAuthor(1, updateDTO);

        assertEquals("Jane", result.getFirstName());
        assertEquals("updated.jpg", result.getPhoto());
    }

    @Test
    void updateAuthor_throwsWhenUpdatedNameAlreadyExistsForAnotherAuthor() {
        when(repo.findById(1)).thenReturn(Optional.of(author));
        when(repo.existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndAuthorIdNot("Jane", "Doe", 1)).thenReturn(true);

        AuthorRequestDTO updateDTO = AuthorRequestDTO.builder()
                .firstName("Jane")
                .lastName("Doe")
                .photo("updated.jpg")
                .build();

        assertThrows(DuplicateAuthorException.class, () -> service.updateAuthor(1, updateDTO));
    }

    @Test
    void deleteAuthor_returnsSuccessMessage_whenAuthorExists() {
        when(repo.findById(1)).thenReturn(Optional.of(author));

        assertEquals("Author deleted successfully with id: 1", service.deleteAuthor(1));
    }

    @Test
    void addAuthor_throwsWhenRequestIsInvalidOrDuplicate() {
        when(repo.findByFirstNameAndLastName("John", "Doe")).thenReturn(author);

        assertThrows(NullPointerException.class, () -> service.addAuthor(null));
        assertThrows(DuplicateAuthorException.class, () -> service.addAuthor(requestDTO));
    }

    @Test
    void authorLookupOperations_throwWhenDataIsMissing() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> service.getAuthorById(99));
    }

    @Test
    void updateAndDelete_throwWhenAuthorDoesNotExist() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> service.updateAuthor(99, requestDTO));
        assertThrows(AuthorNotFoundException.class, () -> service.deleteAuthor(99));
    }

    @Test
    void getAllAuthors_handlesEmptyResults() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        assertTrue(service.getAllAuthors().isEmpty());
    }
}
