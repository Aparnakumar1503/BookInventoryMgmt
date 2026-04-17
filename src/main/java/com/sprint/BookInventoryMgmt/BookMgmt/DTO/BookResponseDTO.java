package com.sprint.BookInventoryMgmt.BookMgmt.DTO;

public class BookResponseDTO {

    private String isbn;
    private String title;
    private String description;
    private String edition;
    private Integer categoryId;
    private Integer publisherId;

    // Constructor
    public BookResponseDTO(String isbn, String title, String description,
                           String edition, Integer categoryId, Integer publisherId) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.edition = edition;
        this.categoryId = categoryId;
        this.publisherId = publisherId;
    }

    //  Getters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getEdition() { return edition; }
    public Integer getCategoryId() { return categoryId; }
    public Integer getPublisherId() { return publisherId; }
}