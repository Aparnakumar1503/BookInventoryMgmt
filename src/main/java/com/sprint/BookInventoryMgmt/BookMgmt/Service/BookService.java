package com.sprint.BookInventoryMgmt.bookmgmt.service;



import com.sprint.BookInventoryMgmt.bookmgmt.dto.request.BookRequestDTO;
import com.sprint.BookInventoryMgmt.bookmgmt.dto.response.BookResponseDTO;

import java.util.List;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO dto);

    BookResponseDTO getBookByIsbn(String isbn);

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO updateBook(String isbn, BookRequestDTO dto);

    void deleteBook(String isbn);

    List<BookResponseDTO> getBooksByCategoryAndPublisher(Integer catId, Integer publisherId);
}