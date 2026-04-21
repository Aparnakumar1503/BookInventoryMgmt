package com.sprint.bookinventorymgmt.authormgmt.service;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.entity.Author;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.AuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.InvalidBookDataException;
import com.sprint.bookinventorymgmt.authormgmt.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AuthorServiceImpl implements IAuthorService {

    @Autowired
    private AuthorRepository repo;

    public AuthorServiceImpl(AuthorRepository repo) {
        this.repo = repo;
    }

    @Override
    public AuthorResponseDTO addAuthor(AuthorRequestDTO dto) {
        validateAuthorRequest(dto);

        Author existingAuthor = repo.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName());
        if (existingAuthor != null) {
            throw new DuplicateAuthorException(
                    "Author already exists with name: " + dto.getFirstName() + " " + dto.getLastName());
        }

        Author entity = new Author();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoto(dto.getPhoto());

        Author saved = repo.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public List<AuthorResponseDTO> getAllAuthors() {
        List<Author> authors = repo.findAll();
        List<AuthorResponseDTO> response = new ArrayList<>();

        for (Author author : authors) {
            response.add(mapToDTO(author));
        }

        return response;
    }

    @Override
    public AuthorResponseDTO getAuthorById(Integer authorId) {
        Author author = repo.findById(authorId)
                .orElseThrow(() ->
                        new AuthorNotFoundException("Author not found with id: " + authorId));
        return mapToDTO(author);
    }

    @Override
    public AuthorResponseDTO updateAuthor(Integer authorId, AuthorRequestDTO dto) {
        validateAuthorRequest(dto);

        Author author = repo.findById(authorId)
                .orElseThrow(() ->
                        new AuthorNotFoundException("Author not found with id: " + authorId));

        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setPhoto(dto.getPhoto());

        Author updated = repo.save(author);
        return mapToDTO(updated);
    }

    @Override
    public String deleteAuthor(Integer authorId) {
        Author author = repo.findById(authorId)
                .orElseThrow(() ->
                        new AuthorNotFoundException("Author not found with id: " + authorId));
        repo.delete(author);
        return "Author deleted successfully with id: " + authorId;
    }

    @Override
    public AuthorResponseDTO getAuthorByFirstNameAndLastName(String firstName, String lastName) {
        Author author = repo.findByFirstNameAndLastName(firstName, lastName);
        if (author == null) {
            throw new AuthorNotFoundException("Author not found with name: " + firstName + " " + lastName);
        }
        return mapToDTO(author);
    }

    @Override
    public List<AuthorResponseDTO> searchByLastName(String keyword) {
        List<Author> authors = repo.searchByLastName(keyword);
        if (authors.isEmpty()) {
            throw new AuthorNotFoundException("No authors found with keyword: " + keyword);
        }

        List<AuthorResponseDTO> response = new ArrayList<>();
        for (Author author : authors) {
            response.add(mapToDTO(author));
        }

        return response;
    }

    @Override
    public Long countAllAuthors() {
        return repo.countAllAuthors();
    }

    private AuthorResponseDTO mapToDTO(Author entity) {
        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setAuthorId(entity.getAuthorId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoto(entity.getPhoto());
        return dto;
    }

    private void validateAuthorRequest(AuthorRequestDTO dto) {
        if (dto == null
                || dto.getFirstName() == null || dto.getFirstName().isBlank()
                || dto.getLastName() == null || dto.getLastName().isBlank()) {
            throw new InvalidBookDataException("Author first name and last name cannot be null or empty");
        }
    }
}
