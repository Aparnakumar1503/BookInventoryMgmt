package com.sprint.bookinventorymgmt.bookmgmt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookRequestDTO {

    @NotBlank(message = "ISBN is required")
    @Size(min = 3, max = 20, message = "ISBN must be between 3 and 20 characters")
    private String isbn;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "Edition is required")
    @Size(min = 1, max = 20, message = "Edition must be between 1 and 20 characters")
    private String edition;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    @NotNull(message = "Publisher ID is required")
    private Integer publisherId;

    public BookRequestDTO() {}

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public Integer getPublisherId() { return publisherId; }
    public void setPublisherId(Integer publisherId) { this.publisherId = publisherId; }
}