package com.sprint.BookInventoryMgmt.authormgmt.service;

import com.sprint.BookInventoryMgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.BookInventoryMgmt.authormgmt.dto.responsedto.AuthorResponseDTO;

import java.util.List;

public interface IAuthorService {
    AuthorResponseDTO addAuthor(AuthorRequestDTO requestDTO);
    List<AuthorResponseDTO> getAllAuthors();
    AuthorResponseDTO getAuthorById(Integer authorId);
    AuthorResponseDTO updateAuthor(Integer authorId, AuthorRequestDTO requestDTO);
    String deleteAuthor(Integer authorId);
    AuthorResponseDTO getAuthorByFirstNameAndLastName(String firstName, String lastName);
    List<AuthorResponseDTO> searchByLastName(String keyword);
    Long countAllAuthors();

}