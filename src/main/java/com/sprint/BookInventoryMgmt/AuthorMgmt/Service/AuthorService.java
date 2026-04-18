package com.sprint.BookInventoryMgmt.AuthorMgmt.Service;

import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.responsedto.AuthorResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface AuthorService {

    AuthorResponseDTO createAuthor(@Valid AuthorRequestDTO author);

    AuthorResponseDTO getAuthorById(Integer id);

    List<AuthorResponseDTO> getAllAuthors();

    AuthorResponseDTO updateAuthor(Integer id, AuthorRequestDTO author);

    void deleteAuthor(Integer id);
}