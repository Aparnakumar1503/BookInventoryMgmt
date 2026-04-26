package com.sprint.bookinventorymgmt.authormgmt.service;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.entity.Author;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.AuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.DuplicateAuthorException;
import com.sprint.bookinventorymgmt.authormgmt.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
public class AuthorServiceImpl implements IAuthorService {

    @Autowired
    private AuthorRepository repo;

    public AuthorServiceImpl(AuthorRepository repo) {
        this.repo = repo;
    }

    @Override
    public AuthorResponseDTO addAuthor(AuthorRequestDTO dto) {
        Objects.requireNonNull(dto, "Author request cannot be null");

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
        Objects.requireNonNull(dto, "Author request cannot be null");

        Author author = repo.findById(authorId)
                .orElseThrow(() ->
                        new AuthorNotFoundException("Author not found with id: " + authorId));

        if (repo.existsByFirstNameIgnoreCaseAndLastNameIgnoreCaseAndAuthorIdNot(
                dto.getFirstName(), dto.getLastName(), authorId)) {
            throw new DuplicateAuthorException(
                    "Author already exists with name: " + dto.getFirstName() + " " + dto.getLastName());
        }

        if (isAuthorUnchanged(author, dto)) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "No changes detected for author id: " + authorId);
        }

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

    private AuthorResponseDTO mapToDTO(Author entity) {
        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setAuthorId(entity.getAuthorId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoto(entity.getPhoto());
        return dto;
    }

    private boolean isAuthorUnchanged(Author author, AuthorRequestDTO dto) {
        return Objects.equals(author.getFirstName(), dto.getFirstName())
                && Objects.equals(author.getLastName(), dto.getLastName())
                && Objects.equals(normalizePhoto(author.getPhoto()), normalizePhoto(dto.getPhoto()));
    }

    private String normalizePhoto(String photo) {
        return photo == null ? "" : photo.trim();
    }
}
