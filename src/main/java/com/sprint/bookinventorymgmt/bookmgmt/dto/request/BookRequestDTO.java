package com.sprint.BookInventoryMgmt.bookmgmt.dto.request;

public class BookRequestDTO {

    private String isbn;
    private String title;
    private String description;
    private String edition;

    private Integer categoryId;
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