package com.sprint.BookInventoryMgmt.authorMgmt.service;

import com.sprint.BookInventoryMgmt.authorMgmt.entity.BookAuthor;
import com.sprint.BookInventoryMgmt.authorMgmt.entity.BookAuthorId;

import java.util.List;

public interface IBookAuthorService {

    BookAuthor assignAuthorToBook(BookAuthor bookAuthor);

    List<BookAuthor> getAllMappings();

    void removeMapping(BookAuthorId id);
}
