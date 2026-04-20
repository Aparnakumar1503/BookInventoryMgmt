package com.sprint.bookinventorymgmt.authormgmt.service;

import com.sprint.bookinventorymgmt.authormgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.bookinventorymgmt.authormgmt.dto.responsedto.AuthorResponseDTO;
import com.sprint.bookinventorymgmt.authormgmt.entity.Author;
import com.sprint.bookinventorymgmt.authormgmt.exceptions.AuthorNotFoundException;
import com.sprint.bookinventorymgmt.authormgmt.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Author entity = new Author();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoto(dto.getPhoto());

        Author saved = repo.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public List<AuthorResponseDTO> getAllAuthors() {
        return repo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
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
        return authors.stream().map(this::mapToDTO).toList();
    }

    @Override
    public Long countAllAuthors() {
        return repo.countAllAuthors();
    }

    private AuthorResponseDTO mapToDTO(Author entity) {
        return AuthorResponseDTO.builder()
                .authorId(entity.getAuthorId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .photo(entity.getPhoto())
                .build();
    }
}