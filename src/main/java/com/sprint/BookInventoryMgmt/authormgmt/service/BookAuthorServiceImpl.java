package com.sprint.BookInventoryMgmt.authormgmt.service;

import com.sprint.BookInventoryMgmt.authormgmt.entity.BookAuthor;
import com.sprint.BookInventoryMgmt.authormgmt.entity.BookAuthorId;
import com.sprint.BookInventoryMgmt.authormgmt.repository.BookAuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    private final BookAuthorRepository repository;

    public BookAuthorServiceImpl(BookAuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookAuthor assignAuthorToBook(BookAuthor bookAuthor) {
        return repository.save(bookAuthor);
    }

    @Override
    public List<BookAuthor> getAllMappings() {
        return repository.findAll();
    }

    @Override
    public void removeMapping(BookAuthorId id) {
        repository.deleteById(id);
    }
}