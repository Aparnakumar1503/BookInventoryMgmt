package com.sprint.BookInventoryMgmt.AuthorMgmt.Service;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthor;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthorId;

import java.util.List;

public interface BookAuthorService {

    BookAuthor assignAuthorToBook(BookAuthor bookAuthor);

    List<BookAuthor> getAllMappings();

    void removeMapping(BookAuthorId id);
}
