package com.sprint.BookInventoryMgmt.AuthorMgmt.Service;

import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthor;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Entity.BookAuthorId;
import com.sprint.BookInventoryMgmt.AuthorMgmt.Repository.BookAuthorRepository;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.requestdto.BookAuthorRequestDTO;
import com.sprint.BookInventoryMgmt.AuthorMgmt.dto.responsedto.BookAuthorResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    private final BookAuthorRepository repository;

    public BookAuthorServiceImpl(BookAuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookAuthorResponseDTO assignAuthorToBook(BookAuthorRequestDTO dto) {

        BookAuthor entity = new BookAuthor();
        entity.setIsbn(dto.getIsbn());
        entity.setAuthorId(dto.getAuthorId());
        entity.setPrimaryAuthor(dto.getPrimaryAuthor());

        return mapToResponse(repository.save(entity));
    }

    @Override
    public List<BookAuthorResponseDTO> getAllMappings() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void removeMapping(BookAuthorId id) {
        repository.deleteById(id);
    }

    private BookAuthorResponseDTO mapToResponse(BookAuthor entity) {
        BookAuthorResponseDTO dto = new BookAuthorResponseDTO();

        dto.setIsbn(entity.getIsbn());
        dto.setAuthorId(entity.getAuthorId());
        dto.setPrimaryAuthor(entity.getPrimaryAuthor());

        if (entity.getAuthor() != null) {
            dto.setAuthorName(entity.getAuthor().getFirstName() + " " +
                    entity.getAuthor().getLastName());
        }

        if (entity.getBook() != null) {
            dto.setBookTitle(entity.getBook().getTitle());
        }

        return dto;
    }
}