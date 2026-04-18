package com.sprint.BookInventoryMgmt.AuthorMgmt.Service;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthorId;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.responsedto.BookAuthorResponseDTO;

import java.util.List;

public interface BookAuthorService {

    BookAuthorResponseDTO assignAuthorToBook(BookAuthorRequestDTO bookAuthor);

    List<BookAuthorResponseDTO> getAllMappings();

    void removeMapping(BookAuthorId id);
}
