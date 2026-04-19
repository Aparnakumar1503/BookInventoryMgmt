package com.sprint.bookinventorymgmt.authormgmt.service;

import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthor;
import com.sprint.bookinventorymgmt.authormgmt.entity.BookAuthorId;

import java.util.List;

public interface BookAuthorService {

    BookAuthor assignAuthorToBook(BookAuthor bookAuthor);

    List<BookAuthor> getAllMappings();

    void removeMapping(BookAuthorId id);
}
