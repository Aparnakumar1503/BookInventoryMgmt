package com.sprint.BookInventoryMgmt.AuthorMgmt.Service;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.Author;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.AuthorRepository;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.requestdto.AuthorRequestDTO;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.responsedto.AuthorResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
        Author author = new Author();
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setPhoto(dto.getPhoto());

        return mapToResponse(repository.save(author));
    }

    @Override
    public AuthorResponseDTO getAuthorById(Integer id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return mapToResponse(author);
    }

    @Override
    public List<AuthorResponseDTO> getAllAuthors() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AuthorResponseDTO updateAuthor(Integer id, AuthorRequestDTO dto) {
        Author existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setPhoto(dto.getPhoto());

        return mapToResponse(repository.save(existing));
    }

    @Override
    public void deleteAuthor(Integer id) {
        repository.deleteById(id);
    }

    private AuthorResponseDTO mapToResponse(Author author) {
        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setAuthorId(author.getAuthorId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        dto.setPhoto(author.getPhoto());
        return dto;
    }
}