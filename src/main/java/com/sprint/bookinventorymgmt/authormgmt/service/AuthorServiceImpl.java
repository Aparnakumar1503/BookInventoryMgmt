package com.sprint.bookinventorymgmt.authormgmt.service;

import com.sprint.bookinventorymgmt.authormgmt.entity.Author;
import com.sprint.bookinventorymgmt.authormgmt.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author createAuthor(Author author) {
        return repository.save(author);
    }

    @Override
    public Author getAuthorById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Override
    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    @Override
    public Author updateAuthor(Integer id, Author author) {
        Author existing = getAuthorById(id);
        existing.setFirstName(author.getFirstName());
        existing.setLastName(author.getLastName());
        existing.setPhoto(author.getPhoto());
        return repository.save(existing);
    }

    @Override
    public void deleteAuthor(Integer id) {
        repository.deleteById(id);
    }
}