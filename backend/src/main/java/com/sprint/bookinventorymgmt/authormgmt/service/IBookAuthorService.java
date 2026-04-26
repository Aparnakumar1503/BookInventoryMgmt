package com.sprint.bookinventorymgmt.authormgmt.service;



import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.BookAuthorResponseDTO;

import java.util.List;

public interface IBookAuthorService {

    BookAuthorResponseDTO addBookAuthor(BookAuthorRequestDTO requestDTO);
    List<BookAuthorResponseDTO> getAllBookAuthors();
    String deleteByIsbnAndAuthorId(String isbn, Integer authorId);
    List<BookAuthorResponseDTO> findBooksByAuthorId(Integer authorId);
}
