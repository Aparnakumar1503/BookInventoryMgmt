package com.sprint.BookInventoryMgmt.BookMgmt.Mapper;

import com.sprint.BookInventoryMgmt.BookMgmt.DTO.request.BookRequestDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.DTO.response.BookResponseDTO;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Book;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Category;
import com.sprint.BookInventoryMgmt.BookMgmt.Entity.Publisher;

public class BookMapper {

    public static Book toEntity(BookRequestDTO dto, Category category, Publisher publisher) {
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

        BookResponseDTO dto = new BookResponseDTO();

        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setEdition(book.getEdition());

        // ✅ SAFE CATEGORY
        if (book.getCategory() != null) {
            dto.setCategoryId(book.getCategory().getCatId());
            dto.setCategoryName(book.getCategory().getCatDescription());
        }

        // ✅ SAFE PUBLISHER
        if (book.getPublisher() != null) {
            dto.setPublisherId(book.getPublisher().getPublisherId());
            dto.setPublisherName(book.getPublisher().getName());
        }

        return dto;
    }
}