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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest {
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
    void testAddAuthor_Positive() {
        when(repo.findByFirstNameAndLastName("John", "Doe")).thenReturn(null);
        when(repo.save(any(Author.class))).thenReturn(author);

        AuthorResponseDTO result = service.addAuthor(requestDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        verify(repo).save(any(Author.class));
    }

    @Test
    void testGetAllAuthors_Positive() {
        when(repo.findAll()).thenReturn(List.of(author));

        List<AuthorResponseDTO> result = service.getAllAuthors();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(repo).findAll();
    }

    @Test
    void testGetAuthorById_Positive() {
        when(repo.findById(1)).thenReturn(Optional.of(author));

        AuthorResponseDTO result = service.getAuthorById(1);

        assertNotNull(result);
        assertEquals(1, result.getAuthorId());
        assertEquals("John", result.getFirstName());
        verify(repo).findById(1);
    }

    @Test
    void testUpdateAuthor_Positive() {
        Author updated = new Author(1, "Jane", "Doe", "newphoto.jpg");
        AuthorRequestDTO updateDTO = AuthorRequestDTO.builder()
                .firstName("Jane")
                .lastName("Doe")
                .photo("newphoto.jpg")
                .build();

        when(repo.findById(1)).thenReturn(Optional.of(author));
        when(repo.save(any(Author.class))).thenReturn(updated);

        AuthorResponseDTO result = service.updateAuthor(1, updateDTO);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        verify(repo).findById(1);
        verify(repo).save(any(Author.class));
    }

    @Test
    void testDeleteAuthor_Positive() {
        when(repo.findById(1)).thenReturn(Optional.of(author));
        doNothing().when(repo).delete(author);

        String result = service.deleteAuthor(1);

        assertEquals("Author deleted successfully with id: 1", result);
        verify(repo).delete(author);
    }

    @Test
    void testGetAuthorByFirstNameAndLastName_Positive() {
        when(repo.findByFirstNameAndLastName("John", "Doe")).thenReturn(author);

        AuthorResponseDTO result = service.getAuthorByFirstNameAndLastName("John", "Doe");

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        verify(repo).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void testSearchByLastName_Positive() {
        when(repo.searchByLastName("doe")).thenReturn(List.of(author));

        List<AuthorResponseDTO> result = service.searchByLastName("doe");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repo).searchByLastName("doe");
    }

    @Test
    void testCountAllAuthors_Positive() {
        when(repo.countAllAuthors()).thenReturn(5L);

        Long count = service.countAllAuthors();

        assertNotNull(count);
        assertEquals(5L, count);
        verify(repo).countAllAuthors();
    }

    @Test
    void testGetAuthorById_Negative_NotFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> service.getAuthorById(99));
        verify(repo).findById(99);
    }

    @Test
    void testUpdateAuthor_Negative_NotFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> service.updateAuthor(99, requestDTO));
        verify(repo).findById(99);
    }

    @Test
    void testDeleteAuthor_Negative_NotFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> service.deleteAuthor(99));
        verify(repo).findById(99);
    }

    @Test
    void testGetAuthorByFirstNameAndLastName_Negative_NotFound() {
        when(repo.findByFirstNameAndLastName("Unknown", "Person")).thenReturn(null);

        assertThrows(AuthorNotFoundException.class,
                () -> service.getAuthorByFirstNameAndLastName("Unknown", "Person"));
        verify(repo).findByFirstNameAndLastName("Unknown", "Person");
    }

    @Test
    void testSearchByLastName_Negative_NotFound() {
        when(repo.searchByLastName("xyz")).thenReturn(Collections.emptyList());

        assertThrows(AuthorNotFoundException.class, () -> service.searchByLastName("xyz"));
        verify(repo).searchByLastName("xyz");
    }

    @Test
    void testGetAllAuthors_Negative_EmptyList() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        List<AuthorResponseDTO> result = service.getAllAuthors();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repo).findAll();
    }

    @Test
    void testCountAllAuthors_Negative_Zero() {
        when(repo.countAllAuthors()).thenReturn(0L);

        Long count = service.countAllAuthors();

        assertEquals(0L, count);
        verify(repo).countAllAuthors();
    }

    @Test
    void testAddAuthor_Negative_InvalidRequest() {
        assertThrows(InvalidBookDataException.class, () -> service.addAuthor(null));
    }

    @Test
    void testAddAuthor_Negative_DuplicateAuthor() {
        when(repo.findByFirstNameAndLastName("John", "Doe")).thenReturn(author);

        assertThrows(DuplicateAuthorException.class, () -> service.addAuthor(requestDTO));
        verify(repo, never()).save(any(Author.class));
    }

    @Test
    void testUpdateAuthor_Negative_InvalidRequest() {
        assertThrows(InvalidBookDataException.class, () -> service.updateAuthor(1, null));
    }
}
