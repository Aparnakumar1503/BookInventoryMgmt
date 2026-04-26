package com.sprint.bookinventorymgmt.bookmgmt;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.BookRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Book;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Category;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Publisher;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.BookAlreadyExistsException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.BookNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.CategoryNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.DataNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.InvalidInputException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.InvalidIsbnFormatException;
import com.sprint.bookinventorymgmt.bookmgmt.exceptions.PublisherNotFoundException;
import com.sprint.bookinventorymgmt.bookmgmt.repository.BookRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.CategoryRepository;
import com.sprint.bookinventorymgmt.bookmgmt.repository.PublisherRepository;
import com.sprint.bookinventorymgmt.bookmgmt.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void createBook_success() {
        when(bookRepository.existsById("1")).thenReturn(false);
        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category()));
        when(publisherRepository.findById(1)).thenReturn(Optional.of(new Publisher()));
        when(bookRepository.save(any())).thenReturn(new Book());

        assertNotNull(bookService.createBook(validDto()));
        verify(bookRepository).save(any());
    }

    @Test
    void createBook_rejectsDuplicateIsbn() {
        when(bookRepository.existsById("1")).thenReturn(true);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.createBook(validDto()));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void createBook_rejectsMissingCategoryOrPublisher() {
        when(bookRepository.existsById("1")).thenReturn(false);
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> bookService.createBook(validDto()));

        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category()));
        when(publisherRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> bookService.createBook(validDto()));
    }

    @Test
    void getBookByIsbn_success() {
        Book book = new Book();
        book.setIsbn("1");
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        assertNotNull(bookService.getBookByIsbn("1"));
    }

    @Test
    void getBookByIsbn_rejectsInvalidOrMissingIsbn() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(InvalidIsbnFormatException.class, () -> bookService.getBookByIsbn(null));
        assertThrows(InvalidIsbnFormatException.class, () -> bookService.getBookByIsbn(" "));
        assertThrows(BookNotFoundException.class, () -> bookService.getBookByIsbn("1"));
    }

    @Test
    void getAllBooks_returnsAvailableBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(new Book()));

        assertEquals(1, bookService.getAllBooks().size());
    }

    @Test
    void getAllBooks_throwsWhenNoBooksExist() {
        when(bookRepository.findAll()).thenReturn(List.of());

        assertThrows(DataNotFoundException.class, () -> bookService.getAllBooks());
    }

    @Test
    void updateBook_success() {
        Book existing = new Book();
        when(bookRepository.findById("1")).thenReturn(Optional.of(existing));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category()));
        when(publisherRepository.findById(1)).thenReturn(Optional.of(new Publisher()));
        when(bookRepository.save(any())).thenReturn(existing);

        assertNotNull(bookService.updateBook("1", validDto()));
    }

    @Test
    void updateBook_throwsWhenBookOrDependenciesAreMissing() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook("1", validDto()));

        when(bookRepository.findById("1")).thenReturn(Optional.of(new Book()));
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> bookService.updateBook("1", validDto()));

        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category()));
        when(publisherRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(PublisherNotFoundException.class, () -> bookService.updateBook("1", validDto()));
    }

    @Test
    void deleteBook_success() {
        Book book = new Book();
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        assertNotNull(bookService.deleteBook("1"));
        verify(bookRepository).delete(book);
    }

    @Test
    void deleteBook_throwsWhenBookDoesNotExist() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook("1"));
    }

    @Test
    void getBooksByCategory_returnsMatchingBooks() {
        when(categoryRepository.existsById(1)).thenReturn(true);
        when(bookRepository.findByCategoryCatId(1)).thenReturn(List.of(new Book()));

        assertEquals(1, bookService.getBooksByCategory(1).size());
    }

    @Test
    void getBooksByCategory_throwsForInvalidMissingOrEmptyResults() {
        assertThrows(InvalidInputException.class, () -> bookService.getBooksByCategory(null));

        when(categoryRepository.existsById(1)).thenReturn(false);
        assertThrows(CategoryNotFoundException.class, () -> bookService.getBooksByCategory(1));

        when(categoryRepository.existsById(1)).thenReturn(true);
        when(bookRepository.findByCategoryCatId(1)).thenReturn(List.of());
        assertThrows(DataNotFoundException.class, () -> bookService.getBooksByCategory(1));
    }

    @Test
    void getBooksByPublisher_handlesSuccessAndFailureScenarios() {
        when(publisherRepository.existsById(1)).thenReturn(true);
        when(bookRepository.findByPublisherPublisherId(1)).thenReturn(List.of(new Book()));
        assertEquals(1, bookService.getBooksByPublisher(1).size());

        assertThrows(InvalidInputException.class, () -> bookService.getBooksByPublisher(null));

        when(publisherRepository.existsById(2)).thenReturn(false);
        assertThrows(PublisherNotFoundException.class, () -> bookService.getBooksByPublisher(2));
    }

    @Test
    void getBooksByCategoryAndPublisher_handlesSuccessAndFailureScenarios() {
        when(categoryRepository.existsById(1)).thenReturn(true);
        when(publisherRepository.existsById(1)).thenReturn(true);
        when(bookRepository.findByCategoryCatIdAndPublisherPublisherId(1, 1)).thenReturn(List.of(new Book()));
        assertEquals(1, bookService.getBooksByCategoryAndPublisher(1, 1).size());

        assertThrows(InvalidInputException.class, () -> bookService.getBooksByCategoryAndPublisher(null, 1));

        when(bookRepository.findByCategoryCatIdAndPublisherPublisherId(1, 1)).thenReturn(List.of());
        assertThrows(DataNotFoundException.class, () -> bookService.getBooksByCategoryAndPublisher(1, 1));
    }

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
