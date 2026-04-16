package com.sprint.BookInventoryMgmt.AuthorMgmt.Service;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthor;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthorId;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.BookAuthorRepository;
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