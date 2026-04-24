package com.sprint.bookinventorymgmt.authormgmt.service;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.entity.Author;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.AuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.InvalidBookDataException;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
    void addAuthor_success() {
        when(repo.findByFirstNameAndLastName("John", "Doe")).thenReturn(null);
        when(repo.save(any(Author.class))).thenReturn(author);

        AuthorResponseDTO result = service.addAuthor(requestDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(repo).save(any(Author.class));
    }

    @Test
    void addAuthor_duplicate() {
        when(repo.findByFirstNameAndLastName("John", "Doe")).thenReturn(author);

        assertThrows(DuplicateAuthorException.class, () -> service.addAuthor(requestDTO));
        verify(repo, never()).save(any(Author.class));
    }

    @Test
    void addAuthor_invalidRequest() {
        assertThrows(InvalidBookDataException.class, () -> service.addAuthor(null));
    }

    @Test
    void getAllAuthors_success() {
        when(repo.findAll()).thenReturn(List.of(author));

        List<AuthorResponseDTO> result = service.getAllAuthors();

        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    @Test
    void getAuthorById_success() {
        when(repo.findById(1)).thenReturn(Optional.of(author));

        AuthorResponseDTO result = service.getAuthorById(1);

        assertEquals(1, result.getAuthorId());
        verify(repo).findById(1);
    }

    @Test
    void getAuthorById_notFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> service.getAuthorById(99));
    }

    @Test
    void updateAuthor_success() {
        Author updated = new Author(1, "Jane", "Doe", "newphoto.jpg");
        AuthorRequestDTO updateDTO = AuthorRequestDTO.builder()
                .firstName("Jane")
                .lastName("Doe")
                .photo("newphoto.jpg")
                .build();
        when(repo.findById(1)).thenReturn(Optional.of(author));
        when(repo.save(any(Author.class))).thenReturn(updated);

        AuthorResponseDTO result = service.updateAuthor(1, updateDTO);

        assertEquals("Jane", result.getFirstName());
        verify(repo).save(any(Author.class));
    }

    @Test
    void deleteAuthor_notFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> service.deleteAuthor(99));
    }
}
