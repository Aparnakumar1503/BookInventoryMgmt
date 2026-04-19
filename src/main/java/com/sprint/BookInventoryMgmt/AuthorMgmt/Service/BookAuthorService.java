package com.sprint.BookInventoryMgmt.authormgmt.service;

import com.sprint.BookInventoryMgmt.authormgmt.entity.BookAuthor;
import com.sprint.BookInventoryMgmt.authormgmt.entity.BookAuthorId;

import java.util.List;

public interface BookAuthorService {

    BookAuthor assignAuthorToBook(BookAuthor bookAuthor);

    List<BookAuthor> getAllMappings();

    void removeMapping(BookAuthorId id);
}
