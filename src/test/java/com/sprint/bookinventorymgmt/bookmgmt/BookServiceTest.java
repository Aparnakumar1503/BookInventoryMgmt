package com.sprint.bookinventorymgmt.bookmgmt;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.BookRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.entity.*;
import com.sprint.bookinventorymgmt.bookmgmt.exception.*;
import com.sprint.bookinventorymgmt.bookmgmt.repository.*;
import com.sprint.bookinventorymgmt.bookmgmt.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class BookServiceTest {

    @Mock BookRepository bookRepository;
    @Mock CategoryRepository categoryRepository;
    @Mock PublisherRepository publisherRepository;

    @InjectMocks
    BookServiceImpl bookService;

    // ================= 1: CREATE SUCCESS =================
    @Test
    void createBook_success() {

        BookRequestDTO dto = validDto();

        when(bookRepository.existsById("1")).thenReturn(false);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category()));
        when(publisherRepository.findById(1)).thenReturn(Optional.of(new Publisher()));
        when(bookRepository.save(any())).thenReturn(new Book());

        assertNotNull(bookService.createBook(dto));

        verify(bookRepository).save(any());
    }

    // ================= 2: CREATE DUPLICATE =================
    @Test
    void createBook_duplicate() {

        when(bookRepository.existsById("1")).thenReturn(true);

        assertThrows(BookAlreadyExistsException.class,
                () -> bookService.createBook(validDto()));

        verify(bookRepository, never()).save(any());
    }

    // ================= 3: CATEGORY MISSING =================
    @Test
    void createBook_categoryNotFound() {

        when(bookRepository.existsById("1")).thenReturn(false);
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,
                () -> bookService.createBook(validDto()));
    }

    // ================= 4: PUBLISHER MISSING =================
    @Test
    void createBook_publisherNotFound() {

        when(bookRepository.existsById("1")).thenReturn(false);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category()));
        when(publisherRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class,
                () -> bookService.createBook(validDto()));
    }

    // ================= 5: GET BY ISBN SUCCESS =================
    @Test
    void getBookByIsbn_success() {

        Book book = new Book();
        book.setIsbn("1");

        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        assertNotNull(bookService.getBookByIsbn("1"));

        verify(bookRepository).findById("1");
    }

    // ================= 6: GET BY ISBN NOT FOUND =================
    @Test
    void getBookByIsbn_notFound() {

        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.getBookByIsbn("1"));
    }

    // ================= 7: GET BY ISBN NULL =================
    @Test
    void getBookByIsbn_null() {

        assertThrows(InvalidInputException.class,
                () -> bookService.getBookByIsbn(null));
    }

    // ================= 8: GET BY ISBN BLANK =================
    @Test
    void getBookByIsbn_blank() {

        assertThrows(InvalidInputException.class,
                () -> bookService.getBookByIsbn(" "));
    }

    // ================= 9: GET ALL SUCCESS =================
    @Test
    void getAllBooks_success() {

        when(bookRepository.findAll()).thenReturn(List.of(new Book()));

        var result = bookService.getAllBooks();

        assertEquals(1, result.size());
        verify(bookRepository).findAll();
    }

    // ================= 10: GET ALL EMPTY =================
    @Test
    void getAllBooks_empty() {

        when(bookRepository.findAll()).thenReturn(List.of());

        assertThrows(DataNotFoundException.class,
                () -> bookService.getAllBooks());
    }

    // ================= 11: UPDATE SUCCESS =================
    @Test
    void updateBook_success() {

        Book book = new Book();

        when(bookRepository.findById("1")).thenReturn(Optional.of(book));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category()));
        when(publisherRepository.findById(1)).thenReturn(Optional.of(new Publisher()));
        when(bookRepository.save(any())).thenReturn(book);

        assertNotNull(bookService.updateBook("1", validDto()));

        verify(bookRepository).save(any());
    }

    // ================= 12: UPDATE NOT FOUND =================
    @Test
    void updateBook_notFound() {

        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.updateBook("1", validDto()));
    }

    // ================= 13: DELETE SUCCESS =================
    @Test
    void deleteBook_success() {

        Book book = new Book();

        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        String result = bookService.deleteBook("1");

        assertNotNull(result);
        verify(bookRepository).delete(book);
    }

    // ================= 14: DELETE NOT FOUND =================
    @Test
    void deleteBook_notFound() {

        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.deleteBook("1"));
    }

    // ================= 15: FULL REPOSITORY VERIFICATION =================
    @Test
    void repositoryInteraction_verified() {

        when(bookRepository.findAll()).thenReturn(List.of());

        try {
            bookService.getAllBooks();
        } catch (Exception ignored) {}

        verify(bookRepository, times(1)).findAll();
    }

    // ================= CATEGORY + FILTER TESTS =================

    @Test
    void getBooksByCategory_success() {

        when(categoryRepository.existsById(1)).thenReturn(true);
        when(bookRepository.findByCategoryCatId(1)).thenReturn(List.of(new Book()));

        assertEquals(1, bookService.getBooksByCategory(1).size());
    }

    @Test
    void getBooksByCategory_null() {

        assertThrows(InvalidInputException.class,
                () -> bookService.getBooksByCategory(null));
    }

    @Test
    void getBooksByCategory_empty() {

        when(categoryRepository.existsById(1)).thenReturn(true);
        when(bookRepository.findByCategoryCatId(1)).thenReturn(List.of());

        assertThrows(DataNotFoundException.class,
                () -> bookService.getBooksByCategory(1));
    }

    @Test
    void getBooksByPublisher_success() {

        when(publisherRepository.existsById(1)).thenReturn(true);
        when(bookRepository.findByPublisherPublisherId(1)).thenReturn(List.of(new Book()));

        assertEquals(1, bookService.getBooksByPublisher(1).size());
    }

    @Test
    void getBooksByCategoryAndPublisher_success() {

        when(categoryRepository.existsById(1)).thenReturn(true);
        when(publisherRepository.existsById(1)).thenReturn(true);
        when(bookRepository.findByCategoryCatIdAndPublisherPublisherId(1,1))
                .thenReturn(List.of(new Book()));

        assertEquals(1, bookService.getBooksByCategoryAndPublisher(1,1).size());
    }

    // HELPER
    private BookRequestDTO validDto() {

        BookRequestDTO dto = new BookRequestDTO();
        dto.setIsbn("1");
        dto.setTitle("Test Book");
        dto.setEdition("1");
        dto.setCategoryId(1);
        dto.setPublisherId(1);

        return dto;
    }
}