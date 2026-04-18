package com.sprint.bookinventorymgmt.bookmgmt.service;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.BookRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.dto.response.BookResponseDTO;

import java.util.List;

public interface IBookService {

    BookResponseDTO createBook(BookRequestDTO dto);

    BookResponseDTO getBookByIsbn(String isbn);

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO updateBook(String isbn, BookRequestDTO dto);

    String deleteBook(String isbn);

    List<BookResponseDTO> getBooksByCategory(Integer catId);

    List<BookResponseDTO> getBooksByPublisher(Integer publisherId);

    List<BookResponseDTO> getBooksByCategoryAndPublisher(Integer catId, Integer publisherId);
}