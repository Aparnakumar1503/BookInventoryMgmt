package com.sprint.BookInventoryMgmt.authormgmt.service;



import com.sprint.BookInventoryMgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.BookInventoryMgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;

import java.util.List;

public interface IBookAuthorService {

    BookAuthorResponseDTO addBookAuthor(BookAuthorRequestDTO requestDTO);
    List<BookAuthorResponseDTO> getAllBookAuthors();
    List<BookAuthorResponseDTO> getByIsbn(String isbn);
    List<BookAuthorResponseDTO> getByAuthorId(Integer authorId);
    BookAuthorResponseDTO getPrimaryAuthorByIsbn(String isbn);
    String deleteByIsbn(String isbn);
    List<BookAuthorResponseDTO> findBooksByAuthorId(Integer authorId);
}
