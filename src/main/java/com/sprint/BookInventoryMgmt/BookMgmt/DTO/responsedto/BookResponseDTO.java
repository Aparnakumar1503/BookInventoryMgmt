package com.sprint.BookInventoryMgmt.BookMgmt.DTO.responsedto;

public class BookResponseDTO {

    private String isbn;
    private String title;
    private String description;
    private String edition;

    private Integer categoryId;
    private String categoryName;

    private Integer publisherId;
    private String publisherName;

    public BookResponseDTO() {}

    public BookResponseDTO(String isbn, String title, String description, String edition,
                           Integer categoryId, String categoryName,
                           Integer publisherId, String publisherName) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.edition = edition;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
    }

    // Getters and Setters
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

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Integer getPublisherId() { return publisherId; }
    public void setPublisherId(Integer publisherId) { this.publisherId = publisherId; }

    public String getPublisherName() { return publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }
}