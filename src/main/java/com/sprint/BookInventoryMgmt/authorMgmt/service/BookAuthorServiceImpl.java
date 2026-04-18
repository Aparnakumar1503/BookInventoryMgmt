package com.sprint.BookInventoryMgmt.authorMgmt.service;

import com.sprint.BookInventoryMgmt.authorMgmt.entity.BookAuthor;
import com.sprint.BookInventoryMgmt.authorMgmt.entity.BookAuthorId;
import com.sprint.BookInventoryMgmt.authorMgmt.repository.IBookAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookAuthorServiceImpl implements IBookAuthorService {
   @Autowired
    private IBookAuthorRepository repository;

   public BookAuthorServiceImpl(){
   }

    public BookAuthorServiceImpl(IBookAuthorRepository repository) {
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