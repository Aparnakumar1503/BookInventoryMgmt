package com.sprint.bookinventorymgmt.bookmgmt.mapper;

import com.sprint.bookinventorymgmt.bookmgmt.dto.request.BookRequestDTO;
import com.sprint.bookinventorymgmt.bookmgmt.dto.response.BookResponseDTO;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Book;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Category;
import com.sprint.bookinventorymgmt.bookmgmt.entity.Publisher;

public class BookMapper {

    private BookMapper() {
        // prevent instantiation
    }

    public static Book toEntity(BookRequestDTO dto, Category category, Publisher publisher) {

        if (dto == null) return null;

        Book book = new Book();
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setEdition(dto.getEdition());
        book.setCategory(category);
        book.setPublisher(publisher);

        return book;
    }

    public static BookResponseDTO toResponse(Book book) {

        if (book == null) return null;

        BookResponseDTO dto = new BookResponseDTO();

        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setEdition(book.getEdition());

        // CATEGORY
        if (book.getCategory() != null) {
            dto.setCategoryId(book.getCategory().getCatId());
            dto.setCategoryName(book.getCategory().getCatDescription());
        }

        // PUBLISHER
        if (book.getPublisher() != null) {
            dto.setPublisherId(book.getPublisher().getPublisherId());
            dto.setPublisherName(book.getPublisher().getName());
        }

        return dto;
    }
}