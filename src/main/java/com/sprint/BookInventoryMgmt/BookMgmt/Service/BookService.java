package com.sprint.BookInventoryMgmt.BookMgmt.Service;

import com.sprint.BookInventoryMgmt.BookMgmt.DTO.request.BookRequestDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.DTO.response.BookResponseDTO;

import java.util.List;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO dto);

    BookResponseDTO getBookByIsbn(String isbn);

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO updateBook(String isbn, BookRequestDTO dto);

    void deleteBook(String isbn);

    List<BookResponseDTO> getBooksByCategoryAndPublisher(Integer catId, Integer publisherId);
}