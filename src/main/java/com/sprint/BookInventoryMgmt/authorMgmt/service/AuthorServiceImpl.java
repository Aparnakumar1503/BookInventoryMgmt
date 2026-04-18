package com.sprint.BookInventoryMgmt.authorMgmt.service;

import com.sprint.BookInventoryMgmt.authorMgmt.entity.Author;
import com.sprint.BookInventoryMgmt.authorMgmt.repository.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthorServiceImpl implements IAuthorService {
   @Autowired
   IAuthorRepository authorrepository;
   public AuthorServiceImpl(){
   }
    public AuthorServiceImpl(IAuthorRepository authorrepository){
    }
    @Override
    public Author createAuthor(Author author) {
        return authorrepository.save(author);
    }

    @Override
    public Author getAuthorById(Integer id) {
        return authorrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorrepository.findAll();
    }

    @Override
    public Author updateAuthor(Integer id, Author author) {
        Author existing = getAuthorById(id);
        existing.setFirstName(author.getFirstName());
        existing.setLastName(author.getLastName());
        existing.setPhoto(author.getPhoto());
        return authorrepository.save(existing);
    }

    @Override
    public void deleteAuthor(Integer id) {
        authorrepository.deleteById(id);
    }
}